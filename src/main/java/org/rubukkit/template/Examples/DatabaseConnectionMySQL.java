package org.rubukkit.template.Examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import org.rubukkit.template.BukkitPluginMain;

public class DatabaseConnectionMySQL
{
	private final BukkitPluginMain plugin;
	private String hostname;
	private String database;
	private String username;
	private String password;
	private String prefixes;
	private volatile Connection connection;
	private volatile Statement  statement;
	public DatabaseConnectionMySQL(BukkitPluginMain plugin)
	{
		this.plugin = plugin;
	}
	public boolean connect()
	{
		this.hostname = plugin.getConfig().getString("settings.connection.hostname", "");
		this.database = plugin.getConfig().getString("settings.connection.database", "");
		this.username = plugin.getConfig().getString("settings.connection.username", "");
		this.password = plugin.getConfig().getString("settings.connection.password", "");
		this.prefixes = plugin.getConfig().getString("settings.connection.prefixes", "");
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			final String connectionURL = "jdbc:mysql://" + hostname + '/' + database + "?allowMultiQueries=true&autoReConnect=true";
			connection = DriverManager.getConnection(connectionURL, username, password);
			statement  = connection.createStatement();
			return true;
		} catch(SQLException | ClassNotFoundException | NullPointerException ex) {
			plugin.logger.log(Level.WARNING, "Exception in database connection to {0}:\n{1}",
				new Object[] { hostname, ex });
			disconnect();
		}
		return false;
	}
	public synchronized void disconnect()
	{
		try
		{
			if(statement != null)
				statement.close();
		} catch(SQLException ex) {
		} finally {
			statement = null;
		}
		try
		{
			if(connection != null)
				connection.close();
		} catch(SQLException ex) {
		} finally {
			connection = null;
		}
	}
	public String fullQuotedTableName(String table)
	{
		return "'" + database + "'.'" + prefixes + table + "'";
	}
	public ResultSet executeQuery(String query)
	{
		try
		{
			final ResultSet result = statement.executeQuery(query);
			return result;
		} catch(SQLException ex) {
			plugin.logger.log(Level.WARNING, "Exception in executeQuery:\n{0}", ex);
		}
		return null;
	}
	public boolean executeUpdate(String query)
	{
		try
		{
			return statement.execute(query);
		} catch(SQLException ex) {
			plugin.logger.log(Level.WARNING, "Exception in executeUpdate:\n{0}", ex);
		}
		return false;
	}
}
