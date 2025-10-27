package org.severov_v.db;

import lombok.Getter;
import org.severov_v.repository.ResidentRepository;
import org.severov_v.repository.ResidentRepositoryInMemImpl;

import java.sql.Connection;
import java.sql.DriverManager;

@Getter
public class PgManager implements JDBCManager {
    @Getter(lazy = true)
    private static final PgManager instance = new PgManager();

    private static final String url = "jdbc:postgresql://localhost:5432/complaints_service";
    private static final String username = "postgres";
    private static final String password = "lein";

    @Getter
    private Connection connection;

    private PgManager() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to DB successful.");
            connection.createStatement().execute(
                    "BEGIN;\n" +
                            "\n" +
                            "DO $$ BEGIN\n" +
                            "    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'request_status') THEN\n" +
                            "        CREATE TYPE request_status AS ENUM (\n" +
                            "            'CREATED',\n" +
                            "            'ASSIGNED',\n" +
                            "            'COMPLETED',\n" +
                            "            'CANCELLED',\n" +
                            "            'SUSPENDED',\n" +
                            "            'TRANSFERRED'\n" +
                            "            );\n" +
                            "    END IF;\n" +
                            "END $$;\n" +
                            "\n" +
                            "DO $$ BEGIN\n" +
                            "    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'request_type') THEN\n" +
                            "        CREATE TYPE request_type AS ENUM ('APARTMENT', 'HOUSE');\n" +
                            "    END IF;\n" +
                            "END $$;\n" +
                            "\n" +
                            "CREATE TABLE IF NOT EXISTS residents (\n" +
                            "    id SERIAL PRIMARY KEY,\n" +
                            "    full_name VARCHAR(100) NOT NULL,\n" +
                            "    phone VARCHAR(50),\n" +
                            "    created_at TIMESTAMPTZ DEFAULT now()\n" +
                            ");\n" +
                            "\n" +
                            "CREATE TABLE IF NOT EXISTS requests (\n" +
                            "    id BIGSERIAL PRIMARY KEY,\n" +
                            "    complaint_text TEXT NOT NULL,\n" +
                            "    status request_status NOT NULL DEFAULT 'CREATED',\n" +
                            "    type request_type NOT NULL DEFAULT 'APARTMENT',\n" +
                            "    id_complaining BIGINT NOT NULL,\n" +
                            "    house_address VARCHAR(255) NOT NULL,\n" +
                            "    created_at TIMESTAMPTZ DEFAULT now(),\n" +
                            "    updated_at TIMESTAMPTZ DEFAULT now()\n" +
                            ");\n" +
                            "\n" +
                            "CREATE INDEX IF NOT EXISTS idx_requests_status ON requests(status);\n" +
                            "CREATE INDEX IF NOT EXISTS idx_requests_id_complaining ON requests(id_complaining);\n" +
                            "CREATE INDEX IF NOT EXISTS idx_requests_house_address ON requests(house_address);\n" +
                            "\n" +
                            "COMMIT;"
            );
        } catch (Exception e) {
            System.out.println("Connection failed, check ur db, error: " + e.getMessage());
        }
    }
}
