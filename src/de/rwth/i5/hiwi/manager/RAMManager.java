package de.rwth.i5.hiwi.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.rwth.i5.hiwi.model.RAM;

public class RAMManager implements Manager {

	public static ArrayList<RAM> getAllRAM() {
		ArrayList<RAM> rams = new ArrayList<RAM>();
		String sql = "SELECT * FROM RAM ORDER BY SerialNo";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {
				RAM ram = new RAM();
				ram.setSerialNo(rs.getInt("SerialNo"));
				ram.setManufacturer(rs.getString("Manufacturer"));
				ram.setmB(rs.getInt("MB"));
				rams.add(ram);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return rams;
	}

	public static ArrayList<RAM> getAllRAMsBySystemId(int systemId) {
		ArrayList<RAM> rams = new ArrayList<RAM>();
		String sql = "SELECT ram.SerialNo, ram.Manufacturer, ram.MB FROM pcsystem, ram, includes_ram WHERE pcsystem.SystemID = includes_ram.PCSystem_SystemID AND ram.SerialNo = includes_ram.RAM_SerialNo AND pcsystem.SystemID = ? ORDER BY ram.SerialNo";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			
			stmt.setInt(1, systemId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				RAM ram = new RAM();
				ram.setSerialNo(rs.getInt("SerialNo"));
				ram.setManufacturer(rs.getString("Manufacturer"));
				ram.setmB(rs.getInt("MB"));
				rams.add(ram);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return rams;
	}
	
	public static RAM getRAMBySerialNo(int serialNo) {

		String sql = "SELECT * FROM RAM WHERE SerialNo = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, serialNo);
			rs = stmt.executeQuery();

			if (rs.next()) {
				RAM ram = new RAM();
				ram.setSerialNo(rs.getInt("SerialNo"));
				ram.setManufacturer(rs.getString("Manufacturer"));
				ram.setmB(rs.getInt("MB"));
				return ram;
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static int insert(RAM ram) throws SQLException {
		String sql = "INSERT INTO RAM (Manufacturer, MB) VALUES (?, ?)";
		ResultSet keys = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setString(1, ram.getManufacturer());
			stmt.setInt(2, ram.getmB());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				keys = stmt.getGeneratedKeys();
				keys.next();
				return keys.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			// System.err.println(e.getMessage());
			throw e;
		}
	}

	public static boolean update(RAM ram) {
		String sql = "UPDATE RAM SET Manufacturer = ?, MB = ? WHERE SerialNo = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setString(1, ram.getManufacturer());
			stmt.setInt(2, ram.getmB());

			stmt.setInt(3, ram.getSerialNo());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	public static boolean delete(int serialNo) {
		String sql = "DELETE FROM RAM WHERE SerialNo = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, serialNo);

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
}
