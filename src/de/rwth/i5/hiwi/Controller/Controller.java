package de.rwth.i5.hiwi.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import de.rwth.i5.hiwi.manager.CPUManager;
import de.rwth.i5.hiwi.manager.IncludesCPUManager;
import de.rwth.i5.hiwi.manager.IncludesRAMManager;
import de.rwth.i5.hiwi.manager.PCSystemManager;
import de.rwth.i5.hiwi.manager.RAMManager;
import de.rwth.i5.hiwi.model.CPU;
import de.rwth.i5.hiwi.model.IncludesCPU;
import de.rwth.i5.hiwi.model.IncludesRAM;
import de.rwth.i5.hiwi.model.PCSystem;
import de.rwth.i5.hiwi.model.RAM;

public class Controller {

	public void insertPCStore() throws Exception {
		Scanner scanner = new Scanner(System.in);
		PCSystem pcSystem = new PCSystem();
		System.out.println("Ener Labeling:");
		pcSystem.setLabeling(scanner.next());
		System.out.println("Ener Manufacturer:");
		pcSystem.setManufacturer(scanner.next());
		System.out.println("Ener the path of Hardware Configaration text file:");
		String configFilePath = scanner.next();
		if (storePCSystem(pcSystem, configFilePath)) {
			System.out.println("PCSystem inserted!");
		} else {
			System.out.println("PCSystem can not be inserted!");
		}
	}

	public boolean storePCSystem(PCSystem pcSystem, String filePath) throws Exception {

		int pcSystemKey = PCSystemManager.insert(pcSystem);

		if (!(pcSystemKey == 0)) {
			storeHardwareConfigFile(filePath, pcSystemKey);
			return true;
		} else {
			System.err.println("PCSystem Cannot inserted into Database!");
		}

		return false;
	}

	public boolean storeHardwareConfigFile(String filePath, int pcSystemKey)
			throws Exception {
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		ArrayList<String> lines = new ArrayList<>();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] lineArray = line.split(",");

			if (lineArray[0].equalsIgnoreCase("cpu")) {
				CPU cpu = new CPU();
				cpu.setManufacturer(lineArray[1]);
				cpu.setmHz(new BigDecimal(Float.parseFloat(lineArray[2])));
				int cpuKey = CPUManager.insert(cpu);
				IncludesCPU includesCPU = new IncludesCPU();
				includesCPU.setpCSystem_SystemId(pcSystemKey);
				includesCPU.setcPU_SerialNo(cpuKey);
				IncludesCPUManager.insert(includesCPU);
			} else if (lineArray[0].equalsIgnoreCase("ram")) {
				RAM ram = new RAM();
				ram.setManufacturer(lineArray[1]);
				ram.setmB(Integer.parseInt(lineArray[2]));
				int ramKey = RAMManager.insert(ram);
				IncludesRAM includesRAM = new IncludesRAM();
				includesRAM.setpCSystem_SystemId(pcSystemKey);
				includesRAM.setrAM_SerialNo(ramKey);
				IncludesRAMManager.insert(includesRAM);
			} else {
				throw new Exception("Wrong Syntaxt of text file!");
			}
		}

		return false;
	}

	public void printAllPCSystems() {
		ArrayList<PCSystem> pCSystems = PCSystemManager.getAllPCSystem();
		System.out.println("PCSystems:");

		for (PCSystem pcSystem : pCSystems) {

			System.out.print(pcSystem.getSystemId() + ". ");
			System.out.print(pcSystem.getLabeling());
			System.out.print(" ");
			System.out.println(pcSystem.getManufacturer());

			ArrayList<CPU> cpus = CPUManager.getAllCPUsByPCSystemId(pcSystem.getSystemId());

			System.out.println("CPUs:");
			for (CPU cpu : cpus) {
				System.out.print("        ");
				System.out.print(cpu.getManufacturer());
				System.out.print(" ");
				System.out.println(cpu.getmHz());
			}

			ArrayList<RAM> rams = RAMManager.getAllRAMsBySystemId(pcSystem.getSystemId());

			System.out.println("RAMs:");
			for (RAM ram : rams) {
				System.out.print("        ");
				System.out.print(ram.getManufacturer());
				System.out.print(" ");
				System.out.println(ram.getmB());
			}
		}

	}

	public void exportAllPCSystemstoXML() throws IOException {

		Element pCStoreElement = new Element("PCStore");
		Document doc = new Document(pCStoreElement);

		ArrayList<PCSystem> pCSystems = PCSystemManager.getAllPCSystem();

		// root element

		for (PCSystem pcSystem : pCSystems) {
			// PCSystem Element
			Element pCSystemElement = new Element("PCSystem");
			pCSystemElement.setAttribute(new Attribute("SystemId", String.valueOf(pcSystem.getSystemId())));
			pCSystemElement.setAttribute(new Attribute("Labeling", pcSystem.getLabeling()));
			pCSystemElement.setAttribute(new Attribute("Manufacturer", pcSystem.getManufacturer()));

			ArrayList<CPU> cpus = CPUManager.getAllCPUsByPCSystemId(pcSystem.getSystemId());

			for (CPU cpu : cpus) {
				// CPU Element
				Element cpuElement = new Element("CPU");
				cpuElement.setAttribute("SerialNo", String.valueOf(cpu.getSerialNo()));
				cpuElement.setAttribute("Manufacturer", cpu.getManufacturer());
				cpuElement.setAttribute("MHz", String.valueOf(cpu.getmHz()));
				pCSystemElement.addContent(cpuElement);
			}

			ArrayList<RAM> rams = RAMManager.getAllRAMsBySystemId(pcSystem.getSystemId());

			for (RAM ram : rams) {
				// RAM Element
				Element ramElement = new Element("RAM");
				ramElement.setAttribute(new Attribute("SerialNo", String.valueOf(ram.getSerialNo())));
				ramElement.setAttribute(new Attribute("Manufacturer", ram.getManufacturer()));
				ramElement.setAttribute(new Attribute("MB", String.valueOf(ram.getmB())));
				pCSystemElement.addContent(ramElement);
			}

			doc.getRootElement().addContent(pCSystemElement);
		}
		XMLOutputter xmlOutput = new XMLOutputter();

		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(doc, new FileWriter("Export.xml"));

		System.out.println("Exported to Export.xml");

	}

}
