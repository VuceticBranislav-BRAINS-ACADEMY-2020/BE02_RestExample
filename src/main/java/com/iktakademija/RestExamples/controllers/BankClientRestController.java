package com.iktakademija.RestExamples.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktakademija.RestExamples.entities.BankClientEntities;

@RestController
@RequestMapping("/bankclients")
public class BankClientRestController {

	// Mock data base
	private List<BankClientEntities> DB;

	// Generate or return mock database
	private List<BankClientEntities> getDB() {
		if (DB == null) {
			List<BankClientEntities> clientsFromDB = new ArrayList<BankClientEntities>();
			clientsFromDB.add(new BankClientEntities(1, "Vladimir", "Dimitieski", "a@aaaaaa.aaa", '-', LocalDate.parse("1919-03-29"), "Idjos"));
			clientsFromDB.add(new BankClientEntities(2, "Nebojsa", "Horvat", "a@bbbbbb.aaa", '-', LocalDate.parse("1999-03-09"), "Zrenjanin"));
			clientsFromDB.add(new BankClientEntities(3, "Ive", "Ivic", "ive@ive.aaa", '-', LocalDate.parse("1989-08-19"), "Novi Sad"));
			clientsFromDB.add(new BankClientEntities(4, "Delme", "Delkovic", null, '-', LocalDate.parse("1989-08-19"), "Geograd"));
			clientsFromDB.add(new BankClientEntities(5, null, null, null, null, null, null));
			DB = clientsFromDB;
		}
		// return all clients to web FE
		return DB;
	}

	// GET all - list all clients
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<BankClientEntities> getAll() {
		// get all clients from database
		// return all clients to web FE
		List<BankClientEntities> clientsFromDB = getDB();
		return clientsFromDB;
	}

	// GET by ID - get single client
	@RequestMapping(value = "/{clientId2}", method = RequestMethod.GET)
	public BankClientEntities getByID(@PathVariable("clientId2") Integer clientId) {

		List<BankClientEntities> clientsFromDB = getDB();

		for (BankClientEntities a : clientsFromDB) {
			if (a.getId().equals(clientId))
				return a;
		}
		return null;
	}

	// POST - create new client
	@RequestMapping(value = "", method = RequestMethod.POST)
	public BankClientEntities createNewClient(@RequestBody BankClientEntities newClient) {
		// insert data into DB
		List<BankClientEntities> clientsFromDB = getDB();
		newClient.setId((new Random()).nextInt());
		clientsFromDB.add(newClient);		
		
		// if all is ok, return new entity
		System.out.println("New client added : " + newClient.getName());
		return newClient;
	}
	
	// PUT - Modify existin client in database or return null if client do not exists
	@RequestMapping(value = "/{input}", method = RequestMethod.PUT)
	public BankClientEntities modifyClient(@PathVariable("input") Integer id, @RequestBody BankClientEntities client) {
		// Find client in database
		for (BankClientEntities bce : getDB()) {
			if (bce.getId().equals(id)) {

				// Modify database
				if (client.getName() != null) 	 bce.setName(client.getName());
				if (client.getSurname() != null) bce.setSurname(client.getSurname());
				if (client.getEmail() != null) 	 bce.setEmail(client.getEmail());
				if (client.getBonitet() != null) bce.setBonitet(client.getBonitet());
				if (client.getDatumRodenja() != null) bce.setDatumRodenja(client.getDatumRodenja());
				if (client.getGrad() != null) 	 bce.setGrad(client.getGrad());
				
				// Return modified client
				System.out.println("Client modified.");
				return bce;
			}
		}

		// Return null if object do not exist in database
		System.out.println("Nothing to modify");
		return null;
	}

	// DELETE - Remove client from database. Return null if client id do not exists or deleted object.
	@RequestMapping(value = "/{input}", method = RequestMethod.DELETE)
	public BankClientEntities deleteClient(@PathVariable("input") Integer id) {
		// Get database
		List<BankClientEntities> base = getDB();		
		
		// Find client in database
		for (BankClientEntities bce : getDB()) {
			if (bce.getId().equals(id)) {

				// Modify database
				base.remove(bce);

				// Return modified client
				System.out.println("Client deleted.");
				return bce;
			}
		}

		// Return null if object do not exist in database
		System.out.println("Nothing to delete.");
		return null;
	}
	
	// Find client by name and surename
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<BankClientEntities> findByName(@RequestParam("Name") String name, @RequestParam("Surname") String surname) {
		List<BankClientEntities> result = new ArrayList<BankClientEntities>();
		
		// If client is found by name and surname return it
		for (BankClientEntities bce : getDB()) {
			if (bce.getName().equalsIgnoreCase(name) && 
				bce.getSurname().equalsIgnoreCase(surname)) {	
				System.out.println("Client Found.");
				result.add(bce);
			}
		}
		return result;
	}

	
	
	// Zadatak 1.
	
	// Return all client emails in a list. Skip null objects.
	@RequestMapping(value = "/emails", method=RequestMethod.GET)
	public List<String> getAllEmails() {
		
		// URL:  http://localhost:8090/bankclients/emails

		// Populate result list with all emails
		List<String> list = new ArrayList<String>();
		for (BankClientEntities bce : getDB()) {
			if (bce.getEmail() != null)
				list.add(bce.getEmail());
		}
		return list;
	}

	// Return list of all clients with name that contains given letter
	@RequestMapping(value="/clients/{firstLetter}", method = RequestMethod.GET)
	public List<String> getAllNamesWithFirstLetter(@PathVariable("firstLetter") char chr) {
		
		// URL:  http://localhost:8090/bankclients/clients/i
		
		// Get database
		List<BankClientEntities> base = getDB();
		
		// Populate result list with names that contains given letter
		List<String> list = new ArrayList<String>();
		for (BankClientEntities bce : base) {
			if (bce.getName().toLowerCase().charAt(0) == chr || 
				bce.getName().toUpperCase().charAt(0) == chr)
			{
				list.add(bce.getName());
			}
		}
		return list;
	}
	
	// Return list of all clients with name and surenames that contains given letter.
	// Skip null objects
	@RequestMapping(value="/clients/firstLetters", method = RequestMethod.GET)
	public List<String> getAllNamesAndSurenamesWithLetter(@RequestParam(value="NameChar") Character chrN, @RequestParam(value="SurnameChar") Character chrS) {
		
		// URL:  http://localhost:8090/bankclients/clients/firstLetters
		
		// Populate result list with names that contains given letter
		List<String> list = new ArrayList<String>();
		for (BankClientEntities bce : getDB()) {
			if ((bce.getName().contains(chrN.toString().toLowerCase()) || 
				 bce.getName().contains(chrN.toString().toUpperCase())) &&
				(bce.getSurname().contains(chrS.toString().toLowerCase()) || 
				 bce.getSurname().contains(chrS.toString().toUpperCase())))
			{
				if (bce.getName() != null)
					list.add(bce.getName());
			}
		}
		return list;
	}

	// Return sorted names of clients. Ascending, descending or unchanged.
	// Skip null objects
	@RequestMapping(value="/clients/sort/{order}", method = RequestMethod.GET)
	public List<String> getSortedNames(@PathVariable("order") String sortOrder) {
		
		// URL:  http://localhost:8090/bankclients/clients/sort/DESC
		
		// Get database
		List<BankClientEntities> base = getDB();

		// Populate list with names
		List<String> list = new ArrayList<String>();
		for (BankClientEntities bce : base) {
			if (bce.getName() != null)
				list.add(bce.getName());
		}

		// Sort based on parameter ASC/DESC
		switch (sortOrder.toUpperCase()) {
		case "ASC":
			Collections.sort(list);
			return list;		
		case "DESC":
			Collections.sort(list, Collections.reverseOrder());
			return list;
		default:
			return list;
		}
	}

	
	
	// Zadatak 2.
	
	// Setup bonitet for all clients
	@RequestMapping(value = "/clients/bonitet", method = RequestMethod.PUT)
	public List<BankClientEntities> changeBonitet() {
		
		// URL:  http://localhost:8090/bankclients/clients/bonitet
		
		// Get database
		List<BankClientEntities> base = getDB();
		
		for (BankClientEntities bce : base) {
			if (bce.getAges() > 65) {
				bce.setBonitet('N');
			} else {
				bce.setBonitet('P');
			}
		}
		return base;
	}
	
	// Delete invalid clients from base. Clients with null field are invalid.
	@RequestMapping(value = "/clients/delete", method = RequestMethod.DELETE)
	public List<BankClientEntities> deleteIncompleteClients() {
		
		// URL:  http://localhost:8090/bankclients/clients/delete
		
		// Get database
		List<BankClientEntities> base = getDB();
		
		// Remove from list
//		Iterator<BankClientEntities> i = base.iterator();
//		while (i.hasNext()) {
//			BankClientEntities s = i.next();
//			if (s.isValid() == false)
//				i.remove();
//		}	
		
//		base.removeIf(n -> n.isValid() == false);	
		
		base = base.stream().filter(n -> n.isValid()).collect(Collectors.toList());	
		
		return base;		
	}

	// Return number of clients younger then given age. Ignore invalid clients
	@RequestMapping(value = "/clients/countLess/{years}", method = RequestMethod.GET)
	public Integer countIfYoungerThen(@PathVariable("years") Integer years) {

		// URL: http://localhost:8090/bankclients/clients/countLess/50

		// Get database
		List<BankClientEntities> base = getDB();
		Integer i = 0;

		for (BankClientEntities bce : base) {
			if (bce.isValid()) {
				if (bce.getAges() < years) {
					i++;
				}
			}
		}
		return i;
	}
	
	// Return average number of years for all clients
	@RequestMapping(value = "/clients/averageYears", method = RequestMethod.GET)
	public Double getAverageYears() {
		
		// URL: http://localhost:8090/bankclients/clients/averageYears	
		
		// Determinate average value
		Double sum = 0.0;
		Integer count = 0;
		for (BankClientEntities bce : getDB()) {
			if (bce.isValid()) {
				sum = sum + bce.getAges();
				count++;
			}
		}

		if (count > 0) {
			System.out.println(sum/count);
			return sum / count;
			
		} else
			return 0.0;
	}


	
	// Zadatak 3.
	
	// PUT Change location for client
	@RequestMapping(value = "/clients/changelocation/{clientId}", method = RequestMethod.PUT)
	public BankClientEntities changeLocation(@PathVariable("clientId") Integer id, @RequestParam("Location") String newLocation) {
		
		// URL: http://localhost:8090/bankclients/clients/changelocation/2?Location=Novi sad	
		
		// Get database
		List<BankClientEntities> base = getDB();
		
		// Find client by index
		for (BankClientEntities bce : base) {
			if (bce.getId().equals(id)) {
				// Replace location if found and if exists
				if (newLocation != null) {
					bce.setGrad(newLocation);
					return bce;
				}
			}
		}
		return null;	
	}

	// GET clients that live on given location
	@RequestMapping(value = "/clients/from/{city}", method = RequestMethod.GET)
	public List<BankClientEntities> liveAtLocation(@RequestParam("city") String location) {

		// URL: http://localhost:8090/bankclients/clients/from/Novi sad

		// Get database
		List<BankClientEntities> base = getDB();
		List<BankClientEntities> list = new ArrayList<BankClientEntities>();

		// Generate list of clients that match location
		for (BankClientEntities bce : base) {
			if (bce.getGrad().equalsIgnoreCase(location)) {
				list.add(bce);
			}
		}
		return list;
	}

	// GET clients that match given location and age
	@RequestMapping(value = "/clients/findByCityAndAge", method = RequestMethod.GET)
	public List<BankClientEntities> findByCityAndAge(@RequestParam("City") String city, @RequestParam("Age") Integer age) {
		
		// URL: http://localhost:8090/bankclients/clients/findByCityAndAge?City=Novi Sad&Age=50

		// Get database
		List<BankClientEntities> base = getDB();
		List<BankClientEntities> list = new ArrayList<BankClientEntities>();

		if (city == null || age == null) {
			return null;
		}

		// Generate list of clients that match location
		for (BankClientEntities bce : base) {
			if (bce.getGrad() != null) {
				if (bce.getGrad().equalsIgnoreCase(city) && bce.getAges() < age.intValue()) {
					list.add(bce);					
				}
			}
		}
		return list;
	}

	
	
	// Zadatak 4.
	
	@RequestMapping("/greetings/{name}")
	public String returnGreetings(@PathVariable String name) {
		// URL: http://localhost:8090/bankclients/greetings/Aco		
		return String.format("Hello %s!", name);
	}	
	
	@RequestMapping(value = "/sumaNiza/{n}", method = RequestMethod.GET)
	public Integer returnSumaNiza(@PathVariable("n") Integer number) {

		// URL http://localhost:8090/bankclients/sumaNiza/5

		// Return 0 if invalid parameter
		if (number == null) return 0;
		Integer sum = 0;

		// Make sum
		for (Integer i = 1; i <= number; i++) {
			sum = sum + i;
		}
		return sum;
	}
	
	@RequestMapping(value = "/recnik/{dictionary}", method = RequestMethod.GET)
	public String returnRecnik(@PathVariable("dictionary") String rec) {

		// URL http://localhost:8090/bankclients/recnik/back

//		switch (rec.toUpperCase()) {
//		case "BACK":
//			return "Iza";			
//		case "FRONT":
//			return "Ispred";			
//		default:
//			return String.format("Rec %s nepostoji u recniku", rec);
//		}	

		Hashtable<String, String> recnik = new Hashtable<String, String>();
		recnik.put("BACK", "Iza");
		recnik.put("FRONT", "Ispred");
		
		if (recnik.get(rec) == null)
			return ("Rec " + rec + " ne postoji u recniku.");
		else
			return recnik.get(rec);

	}

}
