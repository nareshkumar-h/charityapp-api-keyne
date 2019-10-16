package com.revature.charityspring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.exception.DBException;
import com.revature.model.DonorActivity;
import com.revature.util.ConnectionUtil;

@Repository
public class FundDAO {
	 @Autowired
	    private DataSource dataSource;
	 public List<DonorActivity> findAll() throws DBException {
			Connection con = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			DonorActivity da = null;
			List<DonorActivity> list = null;
			try {
				 con = dataSource.getConnection();
				String sql = "select name,request_type,amount_funded from donor d,activity a,donation_request dr where d.id=a.user_id and dr.id=a.request_id";
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				list = new ArrayList<>();
				while (rs.next()) {

					da = toRow(rs);
					list.add(da);
				}

			} catch (SQLException e) {

				throw new DBException("Unable to list Donor", e);
			} finally {
				ConnectionUtil.close(con, pst, rs);
			}
			return list;

		}
	 private DonorActivity toRow(ResultSet rs) throws DBException {
			DonorActivity da = null;

			try {

				String name = rs.getString("name");

				String requestType = rs.getString("request_type");
				double amount = rs.getDouble("amount_funded");
				da = new DonorActivity();

				da.setName(name);

				da.setRequestType(requestType);
				da.setAmount(amount);
			} catch (SQLException e) {
				throw new DBException("Unable to display", e);
			}
			return da;
		}

}
