package com.chadiai.appointmentservice.controller;

import com.chadiai.appointmentservice.dto.AppointmentRequest;
import com.chadiai.appointmentservice.dto.AppointmentResponse;
import com.chadiai.appointmentservice.dto.EditAppointmentRequest;
import com.chadiai.appointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createAppointment
            (@RequestBody AppointmentRequest appointmentRequest) {
        appointmentService.createAppointment(appointmentRequest);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
    }

    @PutMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editAppointment(@PathVariable int id, @RequestBody EditAppointmentRequest appointmentRequest) {
        appointmentService.editAppointment(id,appointmentRequest);
    }

    @GetMapping("/auth/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getAllAuthAppointments(@PathVariable int id) {
        List<AppointmentResponse> allAppointments = appointmentService.getAllAppointments();

        List<AppointmentResponse> filteredAppointments = allAppointments.stream()
                .filter(appointment -> appointment.getInitiatorId() == id)
                .toList();

        return filteredAppointments.stream()
                .map(appointment -> String.valueOf(appointment.getId()))
                .collect(Collectors.joining(";"));
    }

    @GetMapping("/{id}/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponse> getAllAppointments(@PathVariable int id) {
        return appointmentService.getAllAppointmentsByUserId(id);
    }
}
