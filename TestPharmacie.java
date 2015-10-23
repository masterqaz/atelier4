import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class TestPharmacie {

	private Pharmacie pharmacie;

	@Rule
	public TestName name = new TestName();

	@Before
	public void messageDebut() {
		System.out.println("D�but du test Pharmacie " + name.getMethodName());
	}

	@After
	public void messageFin() {
		System.out.println("Fin du test Pharmacie " + name.getMethodName());
	}

	@Before
	public void setUp() throws Exception {
		pharmacie = new Pharmacie();
	}

	@After
	public void tearDown() throws Exception {
		pharmacie = null;
	}

	@Test
	public void testPharmacie() {
		assertNotNull(pharmacie);
	}

	@Test
	public void testGetLesClients() {
		assertEquals("les Clients n'on pas �t� appel� ad�quatement", pharmacie
				.getLesClients().size(), 0);
	}

	@Test
	public void testSetLesClients() {
		List<Client> lesClients = new ArrayList<>();
		lesClients.add(new Client("cancer", "cancer", "cancer"));
		pharmacie.setLesClients(lesClients);
		assertEquals("les Clients n'on pas �t� mutt� ad�quatement", pharmacie
				.getLesClients().size(), 1);
	}

	@Test
	public void testGetLesMedicaments() {
		assertEquals("les m�dicament n'on pas �t� appel� ad�quatement",
				pharmacie.getLesMedicaments().size(), 0);
	}

	@Test
	public void testSetLesMedicaments() {
		List<Medicament> lesMedicaments = new ArrayList<>();
		lesMedicaments.add(new Medicament());
		pharmacie.setLesMedicaments(lesMedicaments);
		assertEquals("les m�dicament n'on pas �t� mutt� ad�quatement",
				pharmacie.getLesMedicaments().size(), 1);
	}

	@Test
	public void testSiClientExiste() {
		List<Client> lesClients = new ArrayList<>();
		lesClients.add(new Client("cancer", "cancer", "cancer"));
		pharmacie.setLesClients(lesClients);
		assertTrue("le Client n'existe pas", pharmacie.siClientExiste("cancer"));
		assertFalse("le Client existe", pharmacie.siClientExiste("cancers"));
	}

	@Test
	public void testAjouterClient() {
		pharmacie.ajouterClient("cancer", "cancer", "cancer");
		assertEquals("le Client ne c'est pas ajout�", pharmacie.getLesClients()
				.size(), 1);
	}

	@Test
	public void testGetPrescriptionsClient() {
		assertNull("la prescription n'est pas null", pharmacie.getPrescriptionsClient("cancer"));
		pharmacie.ajouterClient("cancer", "cancer", "cancer");
		assertNull("la prescription n'est pas null", pharmacie.getPrescriptionsClient("asd"));
		pharmacie.getLesClients().get(0).getPrescriptions()
				.add(new Prescription("cancer", 2, 1));
		assertEquals("la prescription ne c'est pas ajout� au client", pharmacie.getPrescriptionsClient("cancer").size(), 1);
	}

	@Test
	public void testServirPrescription() {
		pharmacie.ajouterClient("cancer", "cancer", "cancer");
		pharmacie.getLesClients().get(0).getPrescriptions()
				.add(new Prescription("cancer", 2, 1));
		pharmacie.servirPrescription("cancer", "cancer");
		pharmacie.servirPrescription("cancers", "CANCERS");
		pharmacie.servirPrescription("cancer", "CANCERS");
		assertEquals("la prescription ne c'est pas servi ad�quatement",pharmacie.getLesClients().get(0).getPrescriptions().get(0)
				.getRenouvellements(), 0);
		pharmacie.servirPrescription("cancer", "cancer");
	}

	@Test
	public void testTrouverInteraction() {
		pharmacie.getLesMedicaments().add(new Medicament());
		pharmacie.getLesMedicaments().get(0).setNomMolecule("cancer");
		pharmacie.getLesMedicaments().get(0)
				.setInteractions(new String[] { "cancer" });
		assertFalse("l'int�raction a �t� trouv� alors qu'elle ne devrais pas", pharmacie.trouverInteraction("cancer1", "cancer1"));
		assertTrue("l'int�raction n'a pas �t� trouv�", pharmacie.trouverInteraction("cancer1", "cancer"));
		assertTrue("l'int�raction n'a pas �t� trouv�", pharmacie.trouverInteraction("cancer", "cancer"));
		assertTrue("l'int�raction n'a pas �t� trouv�", pharmacie.trouverInteraction("cancer", "cancer"));
		pharmacie.getLesMedicaments().add(new Medicament());
		pharmacie.getLesMedicaments().get(1).setNomMolecule("cancer");
		pharmacie.getLesMedicaments().get(1)
				.setInteractions(new String[] { "cancer" });
		assertFalse("l'int�raction a �t� trouv� alors qu'elle ne devrais pas", pharmacie.trouverInteraction("cancer1", "cancer1"));
		assertFalse("l'int�raction a �t� trouv� alors qu'elle ne devrais pas", pharmacie.trouverInteraction("cancer", "cancers"));
		assertTrue("l'int�raction n'a pas �t� trouv�", pharmacie.trouverInteraction("cancer", "cancer"));
	}
}
