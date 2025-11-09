package org.severov_v.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.severov_v.entities.Resident;
import org.severov_v.service.ResidentService;
import org.severov_v.service.ResidentServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/residents")
public class ResidentServlet extends HttpServlet {
    private ResidentService service;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.service = ResidentServiceImpl.getInstance();
        } catch (RuntimeException e) {
            throw new ServletException("Failed to initialize ResidentService", e);
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
            List<Resident> residents;
            if (id >= 0) {
                Resident resident = service.getById(id);
                if (resident != null) {
                    residents = List.of(resident);
                } else {
                    residents = List.of();
                    req.setAttribute("error", "Resident not found.");
                }
            } else {
                residents = service.getAll();
            }
            req.setAttribute("residents", residents);
            req.getRequestDispatcher("/WEB-INF/jsp/residents.jsp").forward(req, resp);
            return;
        }

        if ("edit".equals(action)) {
            String idStr = req.getParameter("id");
            long id = parseId(idStr);
            if (id >= 0) {
                Resident resident = service.getById(id);
                if (resident != null) {
                    req.setAttribute("resident", resident);
                } else {
                    req.setAttribute("error", "Resident not found.");
                }
            } else {
                req.setAttribute("error", "Invalid ID for editing.");
            }
            req.getRequestDispatcher("/WEB-INF/jsp/residents.jsp").forward(req, resp);
            return;
        }

        List<Resident> residents = service.getAll();
        req.setAttribute("residents", residents);
        req.getRequestDispatcher("/WEB-INF/jsp/residents.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String phoneNumber = req.getParameter("number");

        String error = null;
        if (name == null || name.trim().isEmpty()) {
            error = "Name is required.";
        } else if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            error = "Phone number is required.";
        }

        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty() && error == null) {
            long id = parseId(idStr);
            if (id >= 0) {
                try {
                    service.update(new String[]{String.valueOf(id), phoneNumber.trim(), name.trim()});
                    sendRedirectToList(req, resp);
                    return;
                } catch (RuntimeException e) {
                    error = "Failed to update resident: " + e.getMessage();
                }
            } else {
                error = "Invalid ID for update.";
            }
        }

        if (error == null) {
            try {
                service.create(new String[]{phoneNumber.trim(), name.trim()});
            } catch (RuntimeException e) {
                error = "Failed to create resident: " + e.getMessage();
            }
        }

        if (error != null) {
            req.setAttribute("error", error);
            try {
                req.getRequestDispatcher("/WEB-INF/jsp/residents.jsp").forward(req, resp);
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
        resp.sendRedirect(req.getContextPath() + "/residents");
    }
}
