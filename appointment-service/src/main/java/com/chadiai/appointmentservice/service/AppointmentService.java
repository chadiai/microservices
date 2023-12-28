package com.chadiai.appointmentservice.service;

import com.chadiai.appointmentservice.dto.AppointmentRequest;
import com.chadiai.appointmentservice.dto.AppointmentResponse;
import com.chadiai.appointmentservice.model.Appointment;
import com.chadiai.appointmentservice.model.Status;
import com.chadiai.appointmentservice.repository.AppointmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @PostConstruct
    public void loadData() {
        if(appointmentRepository.count() <= 0){
            Appointment appointment1 = Appointment.builder()
                    .consultantId(1)
                    .clientId(2)
                    .status(Status.CANCELED)
                    .date(new Date())
                    .title("Appointment 1")
                    .location("C102")
                    .build();
            Appointment appointment2 = Appointment.builder()
                    .consultantId(1)
                    .clientId(2)
                    .status(Status.SCHEDULED)
                    .date(new Date())
                    .title("Appointment 2")
                    .location("C102")
                    .build();

            appointmentRepository.save(appointment1);
            appointmentRepository.save(appointment2);
        }
    }

    public void createAppointment(AppointmentRequest appointmentRequest){
        Appointment appointment = Appointment.builder()
                .consultantId(appointmentRequest.getConsultantId())
                .clientId(appointmentRequest.getClientId())
                .status(appointmentRequest.getStatus())
                .title(appointmentRequest.getTitle())
                .date(appointmentRequest.getDate())
                .location(appointmentRequest.getLocation())
                .build();

        appointmentRepository.save(appointment);
    }

    public void deleteAppointment(int id) {
        if (!appointmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Appointment with ID " + id + " not found.");
        }
        appointmentRepository.deleteById(id);
    }

    public List<AppointmentResponse> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();

        return appointments.stream().map(this::mapToAppointmentResponse).toList();
    }


    private AppointmentResponse mapToAppointmentResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .status(appointment.getStatus())
                .clientId(appointment.getClientId())
                .consultantId(appointment.getConsultantId())
                .title(appointment.getTitle())
                .date(appointment.getDate())
                .location(appointment.getLocation())
                .build();
    }

}