package org.severov_v.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.severov_v.entities.Request;
import org.severov_v.entities.RequestStatus;
import org.severov_v.entities.RequestType;
import org.severov_v.service.RequestService;
import org.severov_v.service.RequestServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/requests")
public class RequestServlet extends HttpServlet {
    private RequestService service;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.service = RequestServiceImpl.getInstance();
        } catch (RuntimeException e) {
            throw new ServletException("Failed to initialize RequestService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            String idStr = req.getParameter("id");
            long id = parseId(idStr);
            if (id >= 0) {
                service.delete(id);
            } else {
                req.setAttribute("error", "Invalid ID for deletion.");
            }
            sendRedirectToList(req, resp);
            return;
        }

        if ("read".equals(action)) {
            String idStr = req.getParameter("id");
            long id = parseId(idStr);
            List<Request> requests;
            if (id >= 0) {
                Request request = service.getById(id);
                if (request != null) {
                    requests = List.of(request);
                } else {
                    requests = List.of();
                    req.setAttribute("error", "Request not found.");
                }
            } else {
                requests = service.getAll();
            }
            req.setAttribute("requests", requests);
            req.getRequestDispatcher("/WEB-INF/jsp/requests.jsp").forward(req, resp);
            return;
        }

        if ("edit".equals(action)) {
            String idStr = req.getParameter("id");
            long id = parseId(idStr);
            if (id >= 0) {
                Request request = service.getById(id);
                if (request != null) {
                    req.setAttribute("request", request);
                } else {
                    req.setAttribute("error", "Request not found.");
                }
            } else {
                req.setAttribute("error", "Invalid ID for editing.");
            }
            req.getRequestDispatcher("/WEB-INF/jsp/requests.jsp").forward(req, resp);
            return;
        }

        List<Request> requests = service.getAll();
        req.setAttribute("requests", requests);
        req.getRequestDispatcher("/WEB-INF/jsp/requests.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String idComplainingStr = req.getParameter("idComplaining");
        String typeStr = req.getParameter("type");
        String houseAddress = req.getParameter("houseAddress");
        String complaintText = req.getParameter("complaintText");
        String statusStr = req.getParameter("status");

        String error = null;
        long idComplaining = -1;
        RequestType type = null;
        RequestStatus status = null;

        try {
            idComplaining = Long.parseLong(idComplainingStr);
            if (idComplaining < 0) throw new NumberFormatException();
        } catch (Exception e) {
            error = "Invalid complaining ID.";
        }

        if (error == null) {
            try {
                type = RequestType.valueOf(typeStr == null ? "" : typeStr.trim().toUpperCase());
            } catch (Exception e) {
                error = "Invalid request type. Use APARTMENT or HOUSE.";
            }
        }

        if (error == null) {
            try {
                status = RequestStatus.valueOf(statusStr == null ? "" : statusStr.trim().toUpperCase());
            } catch (Exception e) {
                error = "Invalid status.";
            }
        }

        if (error == null && (houseAddress == null || houseAddress.trim().isEmpty())) {
            error = "House address is required.";
        }
        if (error == null && (complaintText == null || complaintText.trim().isEmpty())) {
            error = "Complaint text is required.";
        }

        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty() && error == null) {
            long id = parseId(idStr);
            if (id >= 0) {
                try {
                    service.update(new String[]{String.valueOf(id), String.valueOf(idComplaining), type.toString(), houseAddress.trim(), complaintText.trim(), status.toString()});
                    sendRedirectToList(req, resp);
                    return;
                } catch (RuntimeException e) {
                    error = "Failed to update request: " + e.getMessage();
                }
            } else {
                error = "Invalid ID for update.";
            }
        }

        if (error == null) {
            try {
                service.create(new String[]{String.valueOf(idComplaining), type.toString(), houseAddress.trim(), complaintText.trim()});
            } catch (RuntimeException e) {
                error = "Failed to create request: " + e.getMessage();
            }
        }

        if (error != null) {
            req.setAttribute("error", error);
            try {
                req.getRequestDispatcher("/WEB-INF/jsp/requests.jsp").forward(req, resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        sendRedirectToList(req, resp);
    }

    private long parseId(String idStr) {
        if (idStr == null) return -1;
        try {
            return Long.parseLong(idStr);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private void sendRedirectToList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/requests");
    }
}
