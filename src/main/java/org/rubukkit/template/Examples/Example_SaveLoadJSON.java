package org.rubukkit.template.Examples;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import org.rubukkit.template.API.Example_SaveLoadJSON_Fields;
import org.rubukkit.template.BukkitPluginMain;

public class Example_SaveLoadJSON
{
	public  final static String DEMO_FILE_NAME = "testing-save-with-gson.json";
	private final BukkitPluginMain plugin;
	public Example_SaveLoadJSON(BukkitPluginMain plugin)
	{
		this.plugin = plugin;
	}
	public void testSave(File workingDir)
	{
		// Готовим произвольные данные для теста. Я накидал их от фонаря.
		final Example_SaveLoadJSON_Fields testObject = new Example_SaveLoadJSON_Fields();
		testObject.noNoNoPlease = 10;
		testObject.things = new Example_SaveLoadJSON_Fields.InternalThing[]
		{
			new Example_SaveLoadJSON_Fields.InternalThing("ABC"),
			new Example_SaveLoadJSON_Fields.InternalThing("DEF"),
			new Example_SaveLoadJSON_Fields.InternalThing("GHI"),
		};
		// Файл, в который будем сохранять
		final File file = new File(workingDir, DEMO_FILE_NAME);
		// Сериализацию (запись) и десериализацию (восстановление) всегда проводит такой объект:
		final Gson gson = new Gson();
		// Настраиваем, куда будем выводить сериализованные данные
		try(JsonWriter jw = new JsonWriter(new FileWriter(file)))
		{
			jw.setIndent("\t");
			// Запускаем процесс сериализации
			gson.toJson(testObject, Example_SaveLoadJSON_Fields.class, jw);
			// Готово!
		} catch(IOException ex) {
		}
	}
	public void testLoad(File workingDir)
	{
		// Файл, в который будем сохранять
		final File file = new File(workingDir, DEMO_FILE_NAME);
		// Сериализацию (запись) и десериализацию (восстановление) всегда проводит такой объект:
		final Gson gson = new Gson();
		// Настраиваем, куда будем выводить сериализованные данные
		try(JsonReader jr = new JsonReader(new FileReader(file)))
		{
			// Запускаем процесс десериализации
			final Example_SaveLoadJSON_Fields testObject = gson.fromJson(jr, Example_SaveLoadJSON_Fields.class);
			// Готово!
			plugin.logger.log(Level.INFO, "[GSON] Object read, caption: {0}", testObject.caption);
		} catch(JsonIOException | JsonSyntaxException | IOException ex) {
		}
	}
}
