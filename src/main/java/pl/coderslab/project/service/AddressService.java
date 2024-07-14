package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.project.domain.Address;
import pl.coderslab.project.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public void deleteAddressById(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found with id " + id);
        }
        addressRepository.deleteById(id);
    }

    public void deleteAddress(Address address) {
        if(!addressRepository.existsById(address.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address " + address + "not found");
        }
        addressRepository.delete(address);
    }

    public Address updateAddress(Long id, Address address) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(optionalAddress.isPresent()) {
            address.setId(id);
            return addressRepository.save(address);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found with id " + id);
        }
    }
}
