// package com.gft.patient.service;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

// import com.gft.patient.data.PatientDetailsData;
// import com.gft.patient.models.PatientModel;
// import com.gft.patient.repositories.PatientRepository;

// public class PatientDetailsServiceImpl implements UserDetailsService {

//     @Autowired
//     private PatientRepository patientRepository;

//     public PatientDetailsServiceImpl(PatientRepository patientRepository) {
//         this.patientRepository = patientRepository;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         Optional<PatientModel> patientModel = patientRepository.findByEmailContainingIgnoreCase(username);
//         if (patientModel.isEmpty()) {
//             throw new UsernameNotFoundException("Username [" + username + "] not found!!");
//         }
//         return new PatientDetailsData(patientModel);
//     }
// }
