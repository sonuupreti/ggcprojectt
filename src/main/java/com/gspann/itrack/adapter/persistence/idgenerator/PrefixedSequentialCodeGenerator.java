package com.gspann.itrack.adapter.persistence.idgenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.gspann.itrack.adapter.persistence.exception.IdGenerationException;
import com.gspann.itrack.domain.model.business.Account;
import com.gspann.itrack.domain.model.common.type.AbstractAssignable;
import com.gspann.itrack.domain.model.projects.Project;
import com.gspann.itrack.domain.model.staff.Resource;

public class PrefixedSequentialCodeGenerator implements IdentifierGenerator {

	private static final long FTE_SEQUENCE_START = 10000;

	private static final long NON_FTE_SEQUENCE_START = 20000;

	private static final int ACCOUNT_SEQUENCE_START = 1000;

	private static final long PROJECT_CODE_LENGTH = 7;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object entity) throws HibernateException {
		Connection connection = session.connection();
		try {
			if (entity instanceof Account) {
				return generateAccountCode(connection, (Account) entity);
			} else if (entity instanceof Project) {
				return generateProjectCode(connection, (Project) entity);
			} else if (entity instanceof Resource) {
				return generateResourceCode(connection, (Resource) entity);
			} else {
				// Return the explicitly provided code, not generated
				return providedCode(entity);
			}
		} catch (SQLException e) {
			// Exception while closing JDBC connection
			e.printStackTrace();
		}

		throw new IdGenerationException("",
				"ID Generation strategy not defined for Entity class : " + entity.getClass());
	}

	@SuppressWarnings("rawtypes")
	private Serializable providedCode(Object entity) {
		if (entity instanceof AbstractAssignable) {
			return ((AbstractAssignable) entity).code();
		} else {
			throw new IdGenerationException("",
					"The Entity class : " + entity.getClass() + " must be to type AbstractAssignable");
		}
	}

	private String generateAccountCode(Connection connection, Account account) throws SQLException {
		String query = null;
		ResultSet rs = null;
		try {
			Statement statement = connection.createStatement();
			query = "select max(NUM_ID) as HIGH_END from (\r\n"
					+ "	SELECT substring(CODE FROM '[0-9]+') as NUM_ID FROM ACCOUNTS) temp";
			System.out.println("???????????? query -->" + query);
			rs = statement.executeQuery(query);

			if (rs.next()) {
				String highEnd = rs.getString("HIGH_END");
				int nextSeq = highEnd == null ? ACCOUNT_SEQUENCE_START : Integer.valueOf(highEnd) + 1;
				return account.location().state().country().code() + nextSeq;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IdGenerationException("Exception while generating Code, Query : " + query, e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
		}
		throw new IdGenerationException("Failed to generate Code");
	}

	private String generateProjectCode(Connection connection, Project project) throws SQLException {
		String query = null;
		ResultSet rs = null;
		try {
			Statement statement = connection.createStatement();
			query = "select max(NUM_ID) as HIGH_END from (\r\n"
					+ "	SELECT substring(CODE FROM '[0-9]+') as NUM_ID FROM PROJECTS) temp";
			System.out.println("???????????? query -->" + query);
			rs = statement.executeQuery(query);

			if (rs.next()) {
				String highEnd = rs.getString("HIGH_END");
				System.out.println("highEnd --->>" + highEnd);
				long nextSeq = highEnd == null ? 1 : Long.valueOf(highEnd) + 1;
				String projectCode = project.type().code()
						+ String.format("%0" + PROJECT_CODE_LENGTH + "d", nextSeq);
				System.out.println("projectCode --->>" + projectCode);
				return projectCode;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IdGenerationException("Exception while generating Code, Query : " + query, e);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		throw new IdGenerationException("Failed to generate Code");
	}

	private String generateResourceCode(Connection connection, Resource resource) throws SQLException {
		String query = null;
		ResultSet rs = null;
		try {
			Statement statement = connection.createStatement();
			String whereClause = resource.isFTE() ? "where EMP_TYPE_CODE = 'FTE'"
					: "where EMP_TYPE_CODE != 'FTE'";
			query = "select max(NUM_ID) as HIGH_END from (\r\n"
					+ "	SELECT substring(CODE FROM '[0-9]+') as NUM_ID FROM RESOURCES " + whereClause + ") temp";
//			System.out.println("???????????? query -->" + query);
			rs = statement.executeQuery(query);

			if (rs.next()) {
				String highEnd = rs.getString("HIGH_END");
				long nextSeq = highEnd == null
						? resource.isFTE() ? FTE_SEQUENCE_START
								: NON_FTE_SEQUENCE_START
						: Long.valueOf(highEnd) + 1;
				return "" + nextSeq;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IdGenerationException("Exception while generating Code, Query : " + query, e);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		throw new IdGenerationException("Failed to generate Code");
	}

}