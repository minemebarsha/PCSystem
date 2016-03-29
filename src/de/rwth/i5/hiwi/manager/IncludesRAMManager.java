package de.rwth.i5.hiwi.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.rwth.i5.hiwi.model.IncludesRAM;

public class IncludesRAMManager implements Manager {

	public static ArrayList<IncludesRAM> getAllIncludes() {
		ArrayList<IncludesRAM> includesRAMs = new ArrayList<IncludesRAM>();
		String sql = "SELECT * FROM Includes_ram ORDER BY PCSystem_SystemID";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {
				IncludesRAM includesRAM = new IncludesRAM();
				includesRAM.setpCSystem_SystemId(rs.getInt("PCSystem_SystemID"));
				includesRAM.setrAM_SerialNo(rs.getInt("RAM_SerialNo"));
				includesRAMs.add(includesRAM);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return includesRAMs;
	}

	public static ArrayList<IncludesRAM> getIncludesRAMsByPCSystem_SystemID(int pCSystem_SystemID) {
		ArrayList<IncludesRAM> includesRAMs = new ArrayList<IncludesRAM>();
		String sql = "SELECT * FROM Includes_ram WHERE PCSystem_SystemID = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, pCSystem_SystemID);
			rs = stmt.executeQuery();

			while (rs.next()) {
				IncludesRAM includesRAM = new IncludesRAM();
				includesRAM.setpCSystem_SystemId(rs.getInt("PCSystem_SystemID"));
				includesRAM.setrAM_SerialNo(rs.getInt("RAM_SerialNo"));
				includesRAMs.add(includesRAM);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return includesRAMs;
	}

	public static boolean insert(IncludesRAM includesRAM) throws SQLException {
		String sql = "INSERT INTO includes_ram (PCSystem_SystemID, RAM_SerialNo) VALUES (?, ?)";
		ResultSet keys = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setInt(1, includesRAM.getpCSystem_SystemId());
			stmt.setInt(2, includesRAM.getrAM_SerialNo());

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

	public static boolean update(IncludesRAM includesRAM) {
		String sql = "UPDATE includes_ram RAM_SerialNo = ? WHERE PCSystem_SystemID = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, includesRAM.getrAM_SerialNo());

			stmt.setInt(2, includesRAM.getpCSystem_SystemId());

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
		String sql = "DELETE FROM includes_ram WHERE PCSystem_SystemID = ?";
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
