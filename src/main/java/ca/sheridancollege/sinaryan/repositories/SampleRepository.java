package ca.sheridancollege.sinaryan.repositories;

import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.sinaryan.beans.SampleGroup;
import ca.sheridancollege.sinaryan.beans.SampleObject;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class SampleRepository {

	private NamedParameterJdbcTemplate jdbc;

	public void addObject(SampleObject object) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "SELECT * FROM sample_group WHERE name=:gn";
		parameters.addValue("gn", object.getSampleGroup());
		ArrayList<SampleGroup> groups = (ArrayList<SampleGroup>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<SampleGroup>(SampleGroup.class));

		String query2 = "INSERT INTO sample_object(sample_attribute, group_id) VALUES (:sa, :gi)";

		parameters.addValue("sa", object.getSampleAttribute());
		parameters.addValue("gi", groups.getFirst().getId());

		jdbc.update(query2, parameters);

	}

	public ArrayList<SampleGroup> getGroups() {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sample_group";

		ArrayList<SampleGroup> groups = (ArrayList<SampleGroup>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<SampleGroup>(SampleGroup.class));
		return groups;

	}

	public ArrayList<SampleObject> getObjects() {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT so.id, sample_attribute, name AS sample_group FROM "
				+ "sample_object so, sample_group sg WHERE so.group_id=sg.id";

		ArrayList<SampleObject> objects = (ArrayList<SampleObject>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<>(SampleObject.class));

		return objects;
	}

	public SampleObject findObjectById(int id) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sample_object WHERE id=:id";
		parameters.addValue("id", id);
		ArrayList<SampleObject> objects = (ArrayList<SampleObject>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<>(SampleObject.class));

		if (objects.size() > 0)
			return objects.get(0);
		else
			return null;
	}

	public void editObject(SampleObject object) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "SELECT * FROM sample_group WHERE name=:gn";
		parameters.addValue("gn", object.getSampleGroup());
		ArrayList<SampleGroup> groups = (ArrayList<SampleGroup>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<SampleGroup>(SampleGroup.class));

		String query2 = "UPDATE sample_object SET sample_attribute=:sa, group_id=:gi " + "WHERE id=:id";
		parameters.addValue("id", object.getId());
		parameters.addValue("sa", object.getSampleAttribute());
		parameters.addValue("gi", groups.getFirst().getId());

		jdbc.update(query2, parameters);

	}

	public void removeObjectById(int id) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM sample_object WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
	}

	public void addGroup(SampleGroup group) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "INSERT INTO sample_group(name) VALUES (:na)";

		parameters.addValue("na", group.getName());

		jdbc.update(query, parameters);

	}

	public SampleGroup findGroupById(int id) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sample_group WHERE id=:id";
		parameters.addValue("id", id);
		ArrayList<SampleGroup> groups = (ArrayList<SampleGroup>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<SampleGroup>(SampleGroup.class));

		if (groups.size() > 0)
			return groups.get(0);
		else
			return null;
	}

	public void editGroup(SampleGroup group) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "UPDATE sample_group SET name=:na " + "WHERE id=:id";
		parameters.addValue("id", group.getId());
		parameters.addValue("na", group.getName());

		jdbc.update(query, parameters);

	}

	public void removeGroupById(int id) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM sample_group WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
	}

}
