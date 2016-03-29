package de.rwth.i5.hiwi.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.rwth.i5.hiwi.model.CPU;

public class CPUManager implements Manager {

	public static ArrayList<CPU> getAllCPU() {
		ArrayList<CPU> cpus = new ArrayList<CPU>();
		String sql = "SELECT * FROM CPU ORDER BY SerialNo";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {
				CPU cpu = new CPU();
				cpu.setSerialNo(rs.getInt("SerialNo"));
				cpu.setManufacturer(rs.getString("Manufacturer"));
				cpu.setmHz(rs.getBigDecimal("MHz"));
				cpus.add(cpu);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return cpus;
	}

	public static ArrayList<CPU> getAllCPUsByPCSystemId(int systemId) {
		ArrayList<CPU> cpus = new ArrayList<CPU>();
		String sql = "SELECT cpu.SerialNo, cpu.Manufacturer, cpu.MHz FROM pcsystem, cpu, includes_cpu "
				+ " WHERE pcsystem.SystemID = includes_cpu.PCSystem_SystemID "
				+ " AND cpu.SerialNo = includes_cpu.CPU_SerialNo "
				+ " AND pcsystem.SystemID = ? "
				+ " ORDER BY cpu.SerialNo";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, systemId);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				CPU cpu = new CPU();
				cpu.setSerialNo(rs.getInt("SerialNo"));
				cpu.setManufacturer(rs.getString("Manufacturer"));
				cpu.setmHz(rs.getBigDecimal("MHz"));
				cpus.add(cpu);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return cpus;
	}

	public static CPU getCPUBySerialNo(int serialNo) {

		String sql = "SELECT * FROM CPU WHERE SerialNo = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, serialNo);
			rs = stmt.executeQuery();

			if (rs.next()) {
				CPU cpu = new CPU();
				cpu.setSerialNo(rs.getInt("SerialNo"));
				cpu.setManufacturer(rs.getString("Manufacturer"));
				cpu.setmHz(rs.getBigDecimal("MHz"));
				return cpu;
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static int insert(CPU cpu) throws SQLException {
		String sql = "INSERT INTO CPU (Manufacturer, MHz) VALUES (?, ?)";
		ResultSet keys = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setString(1, cpu.getManufacturer());
			stmt.setBigDecimal(2, cpu.getmHz());

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

	public static boolean update(CPU cpu) {
		String sql = "UPDATE CPU SET Manufacturer = ?, MHz = ? WHERE SerialNo = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setString(1, cpu.getManufacturer());
			stmt.setBigDecimal(2, cpu.getmHz());

			stmt.setInt(3, cpu.getSerialNo());

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
		String sql = "DELETE FROM CPU WHERE SerialNo = ?";
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
