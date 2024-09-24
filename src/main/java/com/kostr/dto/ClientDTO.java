package main.java.com.kostr.dto;

import main.java.com.kostr.models.Client;

import java.util.Objects;
import java.util.UUID;

public class ClientDTO {
    private UUID id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private boolean isProfessional;

    public ClientDTO() {
    }

    public ClientDTO(UUID id, String name, String address, String email, String phoneNumber, boolean isProfessional) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isProfessional = isProfessional;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isProfessional() {
        return isProfessional;
    }

    public void setProfessional(boolean professional) {
        isProfessional = professional;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getAttributes() {
        return new String[]{id.toString(), name, address,email, phoneNumber, String.valueOf(isProfessional)};
    }

    @Override
    public String toString() {
        return "Client : {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isProfessional=" + isProfessional +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(id, clientDTO.id);
    }

    public Client dtoToModel() {
        return new Client(id, name, address, email, phoneNumber, isProfessional);
    }

    public static ClientDTO modelToDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getAddress(), client.getEmail(), client.getPhoneNumber(), client.isProfessional());
    }

}
