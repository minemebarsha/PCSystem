package de.rwth.i5.hiwi.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.rwth.i5.hiwi.model.IncludesCPU;

public class IncludesCPUManager implements Manager {

	public static ArrayList<IncludesCPU> getAllIncludesCPU() {
		ArrayList<IncludesCPU> includesCPUs = new ArrayList<IncludesCPU>();
		String sql = "SELECT * FROM Includes_cpu ORDER BY PCSystem_SystemID";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {
				IncludesCPU includesCPU = new IncludesCPU();
				includesCPU.setpCSystem_SystemId(rs.getInt("PCSystem_SystemID"));
				includesCPU.setcPU_SerialNo(rs.getInt("CPU_SerialNo"));
				includesCPUs.add(includesCPU);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return includesCPUs;
	}

	public static ArrayList<IncludesCPU> getIncludesCPUByPCSystem_SystemID(int pCSystem_SystemID) {
		ArrayList<IncludesCPU> includesCPUs = new ArrayList<IncludesCPU>();
		String sql = "SELECT * FROM Includes_CPU WHERE PCSystem_SystemID = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, pCSystem_SystemID);
			rs = stmt.executeQuery();

			while (rs.next()) {
				IncludesCPU includesCPU = new IncludesCPU();
				includesCPU.setpCSystem_SystemId(rs.getInt("PCSystem_SystemID"));
				includesCPU.setcPU_SerialNo(rs.getInt("CPU_SerialNo"));
				includesCPUs.add(includesCPU);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return includesCPUs;
	}

	public static boolean insert(IncludesCPU includesCPU) throws SQLException {
		String sql = "INSERT INTO includes_cpu (PCSystem_SystemID, CPU_SerialNo) VALUES (?, ?)";
		ResultSet keys = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setInt(1, includesCPU.getpCSystem_SystemId());
			stmt.setInt(2, includesCPU.getcPU_SerialNo());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// System.err.println(e.getMessage());
			throw e;
		}
	}

	public static boolean update(IncludesCPU includesCPU) {
		String sql = "UPDATE includes CPU_SerialNo = ? WHERE PCSystem_SystemID = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, includesCPU.getcPU_SerialNo());
			stmt.setInt(2, includesCPU.getpCSystem_SystemId());

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

	public static boolean delete(int pCSystem_SystemID) {
		String sql = "DELETE FROM includes_cpu WHERE PCSystem_SystemID = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, pCSystem_SystemID);

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
