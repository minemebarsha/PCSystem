package de.rwth.i5.hiwi.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.rwth.i5.hiwi.model.PCSystem;

public class PCSystemManager implements Manager{

	public static ArrayList<PCSystem> getAllPCSystem() {
		ArrayList<PCSystem> pCSystems = new ArrayList<PCSystem>();
		String sql = "SELECT * FROM PCSystem ORDER BY SystemID";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {
				PCSystem pcSystem = new PCSystem();
				pcSystem.setSystemId(rs.getInt("SystemID"));
				pcSystem.setLabeling(rs.getString("Labeling"));
				pcSystem.setManufacturer(rs.getString("Manufacturer"));
				pCSystems.add(pcSystem);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return pCSystems;
	}

	public static PCSystem getPCSystemBySystemID(int systemID) {

		String sql = "SELECT * FROM PCSystem WHERE SystemID = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, systemID);
			rs = stmt.executeQuery();

			if (rs.next()) {
				PCSystem pcSystem = new PCSystem();
				pcSystem.setSystemId(rs.getInt("SystemID"));
				pcSystem.setLabeling(rs.getString("Labeling"));
				pcSystem.setManufacturer(rs.getString("Manufacturer"));
				return pcSystem;
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static int insert(PCSystem pcSystem) throws SQLException {
		String sql = "INSERT INTO PCSystem (Labeling, Manufacturer) VALUES (?, ?)";
		ResultSet keys = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			stmt.setString(1, pcSystem.getLabeling());
			stmt.setString(2, pcSystem.getManufacturer());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				keys = stmt.getGeneratedKeys();
				keys.next();
				return keys.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
//			System.err.println(e.getMessage());
			throw e;
		}
	}

	public static boolean update(PCSystem pcSystem) {
		String sql = "UPDATE PCSystem Labeling = ?, Manufacturer = ? WHERE SystemID = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setString(1, pcSystem.getLabeling());
			stmt.setString(2, pcSystem.getManufacturer());

			stmt.setInt(3, pcSystem.getSystemId());

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

	public static boolean delete(int systemID) {
		String sql = "DELETE FROM PCSystem WHERE systemID = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, systemID);

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
