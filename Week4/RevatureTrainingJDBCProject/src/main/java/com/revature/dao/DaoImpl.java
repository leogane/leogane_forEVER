package com.revature.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.ReimbursementDTO;
import com.revature.pojo.ErsUser;
import com.revature.pojo.Reimbursement;
import com.revature.pojo.ReimbursementType;
import com.revature.pojo.RoleType;
import com.revature.pojo.StatusType;
import com.revature.util.ConnectionUtil;

public class DaoImpl implements Dao {

	@Override
	public ErsUser getERSUserByUsername(String username) {
		ErsUser ersUser = null;
		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "SELECT ers_id, ers_password, ers_fn, ers_ln, ers_email, rt_id FROM ers_user WHERE ers_username=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ersUser = new ErsUser(
					rs.getInt("ers_id"),
					rs.getString("ers_fn"),
					rs.getString("ers_ln"),
					username,
					rs.getString("ers_password"),
					rs.getInt("rt_id"),
					rs.getString("ers_email")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ersUser;
	}

	@Override
	public ErsUser getERSUserById(int userId) {
		ErsUser ersUser = null;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT ers_fn, ers_ln, ers_username, ers_password, rt_id, ers_email FROM ers_user WHERE ers_id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ersUser = new ErsUser(userId, rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ersUser;

	}

	@Override
	public boolean submitReimbursementRequest(Reimbursement reimbursement) {
		boolean success = false;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO reimbursement(ers_id, rbt_id, rb_amount, rb_description, rb_receipt) "
					+ "VALUES (?,?,?,?,?)";
			Blob blobData = connection.createBlob();
			blobData.setBytes(1, reimbursement.getReceipt());

			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, reimbursement.getEmployeeId());
			ps.setInt(2, reimbursement.getReimbursementTypeId());
			ps.setDouble(3, reimbursement.getAmount());
			ps.setString(4, reimbursement.getDescription());
			ps.setBlob(5, blobData);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 1) {
				success = true;
			}
			blobData.free();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;

	}

	@Override
	public List<ReimbursementDTO> getPendingReimbursements(ErsUser ersUser) {
		List<ReimbursementDTO> pendingReimbursements = new ArrayList<>();
		int employeeId = ersUser.getErs_id();
		String employeeName = ersUser.getErs_fn() + " " + ersUser.getErs_ln();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT r.rb_id, rt.rbt_name, r.rb_amount, r.rb_submitted, "
					+ " r.rb_description, r.rb_receipt FROM reimbursement r"
					+ " JOIN reimbursement_type rt ON r.rbt_id=rt.rbt_id WHERE ers_id=? AND st_id=1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, employeeId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				byte[] receipt = null;
				String sReceipt = null;
				Blob blob = rs.getBlob(6);
				if (blob != null) {
					receipt = blob.getBytes(1, (int) blob.length());
					blob.free();
					try {
						sReceipt = new String(receipt, "US-ASCII");
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				pendingReimbursements.add(new ReimbursementDTO(rs.getInt(1), employeeName, "", "Pending",
						rs.getString(2), rs.getDouble(3), rs.getString(4), "", rs.getString(5), sReceipt));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pendingReimbursements;

	}

	@Override
	public List<ReimbursementDTO> getResolvedReimbursements(ErsUser ersUser) {
		List<ReimbursementDTO> resolvedReimbursements = new ArrayList<>();
		int employeeId = ersUser.getErs_id();
		String employeeName = ersUser.getErs_fn() + " " + ersUser.getErs_ln();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT r.rb_id, eu.ers_fn, eu.ers_ln, st.st_name, "
					+ "rt.rbt_name, r.rb_amount, r.rb_submitted, "
					+ "r.rb_resolved, r.rb_description, r.rb_receipt FROM reimbursement r "
					+ "JOIN reimbursement_type rt ON r.rbt_id=rt.rbt_id "
					+ "JOIN ers_user eu ON r.manager_id=eu.ers_id " + "JOIN status_type st ON r.st_id=st.st_id "
					+ "WHERE r.ers_id=? AND r.st_id > 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, employeeId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String sReceipt = null;
				Blob blob = rs.getBlob(10);
				byte[] receipt = null;
				if (blob != null) {
					receipt = blob.getBytes(1, (int) blob.length());
					blob.free();
					try {
						sReceipt = new String(receipt, "US-ASCII");
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				resolvedReimbursements.add(new ReimbursementDTO(rs.getInt(1), employeeName,
						rs.getString(2) + " " + rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6),
						rs.getString(7), rs.getString(8), rs.getString(9), sReceipt));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resolvedReimbursements;
	}

	@Override
	public boolean updateERSUser(ErsUser ersUser, boolean updatePassword) {
		boolean success = false;
		String sql = "UPDATE ers_user SET ers_fn=?, ers_ln=?, ers_username=?, "
				+ "ers_email=?, ers_password=? WHERE ers_id=?";
		if (!updatePassword) {
			sql = "UPDATE ers_user SET ers_fn=?, ers_ln=?, ers_username=?, ers_email=? WHERE ers_id=?";
		}
		try (Connection connection = ConnectionUtil.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, ersUser.getErs_fn());
			ps.setString(2, ersUser.getErs_ln());
			ps.setString(3, ersUser.getErs_username());
			ps.setString(4, ersUser.getErs_email());
			if (updatePassword) {
				ps.setString(5, ersUser.getErs_password());
				ps.setInt(6, ersUser.getErs_id());
			} else {
				ps.setInt(5, ersUser.getErs_id());
			}
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 1) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;

	}

	@Override
	public boolean updateReimbursementRequestStatus(Reimbursement reimbursement) {
		boolean success = false;
		String sql = "UPDATE reimbursement SET st_id=?, manager_id=? WHERE rb_id=?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, reimbursement.getStatusTypeId());
			ps.setInt(2, reimbursement.getManagerId());
			ps.setInt(3, reimbursement.getReimbursementId());
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 1) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public List<ErsUser> getAllEmployees() {
		List<ErsUser> ersUserList = new ArrayList<>();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT ers_id, ers_fn, ers_ln, ers_email FROM ers_user WHERE rt_id=100";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ersUserList.add(new ErsUser(rs.getInt(1), rs.getString(2), rs.getString(3), "",
						"", 1, rs.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ersUserList;
	}

	@Override
	public List<ReimbursementDTO> getAllPendingReimbursements() {
		List<ReimbursementDTO> pendingReimbursements = new ArrayList<>();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT r.rb_id, eu.ers_fn, eu.ers_ln, rt.rbt_name, r.rb_amount, r.rb_submitted, "
					+ "r.rb_description, r.rb_receipt FROM reimbursement r "
					+ "JOIN reimbursement_type rt ON r.rbt_id=rt.rbt_id "
					+ "JOIN ers_user eu ON r.ers_id=eu.ers_id WHERE st_id=1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Blob blob = rs.getBlob(8);
				String sReceipt = null;
				byte[] receipt = null;
				if (blob != null) {
					receipt = blob.getBytes(1, (int) blob.length());
					blob.free();
					try {
						sReceipt = new String(receipt, "US-ASCII");
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				String employeeName = rs.getString(2) + " " + rs.getString(3);
				pendingReimbursements.add(new ReimbursementDTO(rs.getInt(1), employeeName, "", "Pending",
						rs.getString(4), rs.getDouble(5), rs.getString(6), "", rs.getString(7), sReceipt));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pendingReimbursements;
	}

	@Override
	public List<ReimbursementDTO> getAllResolvedReimbursements() {
		List<ReimbursementDTO> resolvedReimbursements = new ArrayList<>();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT r.rb_id, eeu.ers_fn, eeu.ers_ln, eu.ers_fn, "
					+ "eu.ers_ln, st.st_name, rt.rbt_name, r.rb_amount, r.rb_submitted, "
					+ "r.rb_resolved, r.rb_description, r.rb_receipt FROM reimbursement r "
					+ "JOIN reimbursement_type rt ON r.rbt_id=rt.rbt_id " + "JOIN ers_user eeu ON r.ers_id=eeu.ers_id "
					+ "JOIN ers_user eu ON r.manager_id=eu.ers_id " + "JOIN status_type st ON r.st_id=st.st_id "
					+ "WHERE r.st_id > 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Blob blob = rs.getBlob(12);
				String sReceipt = null;
				byte[] receipt = null;
				if (blob != null) {
					receipt = blob.getBytes(1, (int) blob.length());
					blob.free();
					try {
						sReceipt = new String(receipt, "US-ASCII");
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				resolvedReimbursements.add(new ReimbursementDTO(rs.getInt(1), rs.getString(2) + " " + rs.getString(3),
						rs.getString(4) + " " + rs.getString(5), rs.getString(6), rs.getString(7), rs.getDouble(8),
						rs.getString(9), rs.getString(10), rs.getString(11), sReceipt));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resolvedReimbursements;
	}

	@Override
	public boolean addEmployee(ErsUser ersUser) {
		return false;
	}

	@Override
	public String getRoleTypeById(int roleTypeId) {
		String roleType = "";
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT rt_name FROM role_type WHERE rt_id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, roleTypeId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				roleType = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roleType;
	}

	@Override
	public List<ReimbursementType> getReimbursementTypes() {
		List<ReimbursementType> rt = new ArrayList<>();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT rbt_id, rbt_name FROM reimbursement_type";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rt.add(new ReimbursementType(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rt;
	}

	@Override
	public int getReimbursementTypeByName(String name) {
		int typeId = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT rbt_id FROM reimbursement_type WHERE rbt_name=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				typeId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return typeId;
	}

	@Override
	public Reimbursement getReimbursementById(int reimbursementId) {
		Reimbursement reimbursement = null;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT ers_id, manager_id, st_id, rbt_id, rb_amount, rb_submitted, "
					+ "rb_resolved, rb_description, rb_receipt FROM reimbursement WHERE rb_id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, reimbursementId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Blob blob = rs.getBlob(9);
				int blobLength = (int) blob.length();
				byte[] blobAsBytes = blob.getBytes(1, blobLength);

				reimbursement = new Reimbursement(reimbursementId, rs.getInt(1), rs.getInt(2), rs.getInt(3),
						rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getString(8), blobAsBytes);
				blob.free();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reimbursement;
	}

	@Override
	public ReimbursementDTO getReimbursementDtoById(int reimbursementId) {
		// TODO Auto-generated method stub
		return null;
	}
}
