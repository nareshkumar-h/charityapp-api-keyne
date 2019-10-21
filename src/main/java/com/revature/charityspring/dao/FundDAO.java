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

import com.revature.charityspring.dto.FundDto;
import com.revature.charityspring.exception.DBException;
import com.revature.charityspring.model.DonationRequest;
import com.revature.charityspring.util.ConnectionUtil;




@Repository
public class FundDAO {
	 @Autowired
	    private DataSource dataSource;
	 public List<FundDto> findAll() throws DBException {
			Connection con = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			FundDto fund = null;
			List<FundDto> list = null;
			try {
				 con = dataSource.getConnection();
				String sql = "select request_type,request_amount,amount_funded,email_id,name"
						+ " from donation_request dr,activity a,donor d \r\n" + 
						"where dr.id=a.request_id and a.user_id=d.id;";
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				list = new ArrayList<>();
				while (rs.next()) {

					fund = toRow(rs);
					list.add(fund);
				}

			} catch (SQLException e) {

				throw new DBException("Unable to list Donor", e);
			} finally {
				ConnectionUtil.close(con, pst, rs);
			}
			return list;

		}
	 private FundDto toRow(ResultSet rs) throws DBException {
			FundDto fund = null;

			try {

				String name = rs.getString("name");
  
				String requestType = rs.getString("request_type");
				double amount = rs.getDouble("amount_funded");
				double targetAmount=rs.getDouble("request_amount");
				String email=rs.getString("email_id");
				fund = new FundDto();

				fund.setName(name);

				fund.setRequestType(requestType);
				fund.setEmail(email);
				fund.setFundedAmount(amount);
				fund.setRequestAmount(targetAmount);
			} catch (SQLException e) {
				throw new DBException("Unable to display", e);
			}
			return fund;
		}
	 public List<DonationRequest> findAllRequest() throws DBException {
			Connection con = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			DonationRequest da = null;
			List<DonationRequest> list = null;
			try {
				 con = dataSource.getConnection();
				String sql = "SELECT id,request_type,(request_amount -\r\n" + 
						"(SELECT IFNULL(SUM(amount_funded),0) FROM activity WHERE request_id  = fr.id))\r\n" + 
						" as Fund_needed FROM donation_request fr WHERE \r\n" + 
						
						" request_amount > (SELECT IFNULL(SUM(amount_funded),0) as funded_amount FROM activity WHERE request_id  = fr.id)";
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				list = new ArrayList<>();
				while (rs.next()) {

					da = toRow1(rs);
					list.add(da);
				}

			} catch (SQLException e) {

				throw new DBException("Unable to list fund", e);
			} finally {
				ConnectionUtil.close(con, pst, rs);
			}
			return list;

		}
	 private DonationRequest toRow1(ResultSet rs) throws DBException {
		 DonationRequest da = null;

			try {

				int id = rs.getInt("id");

				String requestType = rs.getString("request_type");
				double amount = rs.getDouble("Fund_needed");
				da = new DonationRequest();

				da.setId(id);

				da.setRequestType(requestType);
				da.setRequestAmount(amount);
			} catch (SQLException e) {
				throw new DBException("Unable to display", e);
			}
			return da;
		}

}
