package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.EmployeeDao;
import pet.store.dao.CustomerDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	//public PetStoreData savePetStore(PetStoreData petStoreData) {
		// TODO Auto-generated method stub
		//return null;
	// }
//

	//1//
	@Autowired 
	private PetStoreDao petStoreDao; 
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;
	


	public PetStoreData savePetStore(PetStoreData petStoreData) {
		PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());
		copyPetStoreFields(petStore, petStoreData); 
		petStore = petStoreDao.save(petStore);
		return new PetStoreData(petStore); 
	}
		
	private PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore;
		
		if (Objects.isNull(petStoreId)) {
			petStore = new PetStore();
		} else {
			petStore = findPetStoreById(petStoreId);
		}
		return petStore;
	}
	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet store with Id=" + petStoreId + "not found."));
		
		}
	
	//2 & 6//
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId); 
		Employee employee = findOrCreateEmployee(petStoreId, petStoreEmployee.getEmployeeId());
		copyEmployeeFields(employee, petStoreEmployee);
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new PetStoreEmployee(dbEmployee); 
	}
	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId); 
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId,customerId);
		copyCustomerFields(customer, petStoreCustomer);
		customer.getPetStores().add(petStore);
		petStore.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new PetStoreCustomer(dbCustomer); 
		
	}
	
	
	//3//
	public Employee findEmployeeById(Long petStoreId, Long employeeId) {
			Employee employee = employeeDao.findById(employeeId) 
				.orElseThrow(() -> new NoSuchElementException(
						"Employee with ID=" + employeeId + " does not exist.")); 
				
				if(employee.getPetStore().getPetStoreId() == petStoreId) {
					return employee;
				} else {
					throw new IllegalArgumentException("Pet store with ID=" + petStoreId + "does not have an employee with ID=" + employeeId); 
				 }
				}
				
	public Customer findCustomerById(Long petStoreId, Long customerId) {
				Customer customer = customerDao.findById(customerId) 
				.orElseThrow(() -> new NoSuchElementException(
							"Customer with ID=" + customerId + " does not exist.")); 
				boolean found = false; 
				for(PetStore petStore : customer.getPetStores()) {
					if(petStore.getPetStoreId() == petStoreId) {
						found = true;
						break;
					}
				}
				if(!found) { 
					throw new IllegalArgumentException("Customer with ID=" + customerId + "is not a member of the pet store with ID=" + petStoreId); 
				}
				return customer;
				
			}
	
	public Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		Customer customer; 
		
		       if(Objects.isNull(customerId)) {
		    	   customer = new Customer();
		       } else {
		    	   customer = findCustomerById(petStoreId, customerId);
		       }
		    	   return customer;
		       
		       
	}
	
	//4//		
	public Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
			Employee employee;
					
				if(Objects.isNull(employeeId)) {
					employee = new Employee();
				} else {
						employee = findEmployeeById(petStoreId, employeeId);
				}
				return employee;
					
				}
	
	//5//
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	}
		
	
public void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
}
	
	

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}

		
	//List all Pet Stores//
	//2//
	
	@Transactional 
	public List<PetStoreData> retrieveAllPetStores() { 
		   List<PetStore> petStores = petStoreDao.findAll();
		   java.util.List<PetStoreData> results = new LinkedList<>(); 
		   
		   for(PetStore petStore : petStores) {
			   PetStoreData petStoreData = new PetStoreData(petStore);
			   
			   petStoreData.getEmployees().clear();
			   petStoreData.getCustomers().clear();
			   
			   results.add(petStoreData);
		   }
		   
		   return results; 
	}
	
	//Retreieve A pet store by store ID//
	
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		return new PetStoreData(petStore);
	}


	
	
	public void deletePetStoreById(Long petStoreId) {
			PetStore petStore = findPetStoreById(petStoreId);
			petStoreDao.delete(petStore);
	}

	
}