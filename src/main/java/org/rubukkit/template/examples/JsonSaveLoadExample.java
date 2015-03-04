package org.rubukkit.template.examples;
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
import org.rubukkit.template.BukkitPluginMain;

public class JsonSaveLoadExample
{
	public static class ClassToSave
	{
		// Сохраняются простые и привычные типы
		public int     id        = 165;
		public String  caption   = "ClassToSave";
		public boolean confirmed = true;
		// К ограничениям отнесём то, что не стоит включать в сериализуемые данные Коллекции (Map, Set, List, др).
		// Наиболее лёгкий способ сохранения множества данных — обычные массивы.
		public static class InternalThing
		{
			public String text;
			public String noText;
			InternalThing(String text)
			{
				this.text = text;
			}
		}
		public InternalThing[] things;
		// Ключевым словом transient помечается то, что никогда не будет сериализовано
		public transient int noNoNoPlease;
		// Если поле равно null, оно будет пропущено при сериализизации
	}
	public static void testSave(File workingDir)
	{
		// Готовим произвольные данные для теста. Я накидал их от фонаря.
		final ClassToSave testObject = new ClassToSave();
		testObject.noNoNoPlease = 10;
		testObject.things = new ClassToSave.InternalThing[]
		{
			new ClassToSave.InternalThing("abc"),
			new ClassToSave.InternalThing("def"),
			new ClassToSave.InternalThing("ghi"),
		};
		// Файл, в который будем сохранять
		final File file = new File(workingDir, "testing-save-with-gson.json");
		// Сериализацию (запись) и десериализацию (восстановление) всегда проводит такой объект:
		final Gson gson = new Gson();
		// Настраиваем, куда будем выводить сериализованные данные
		try(JsonWriter jw = new JsonWriter(new FileWriter(file)))
		{
			jw.setIndent("\t");
			// Запускаем процесс сериализации
			gson.toJson(testObject, ClassToSave.class, jw);
			// Готово!
		} catch(IOException ex) {
		}
	}
	public static void testLoad(File workingDir)
	{
		// Файл, в который будем сохранять
		final File file = new File(workingDir, "testing-save-with-gson.json");
		// Сериализацию (запись) и десериализацию (восстановление) всегда проводит такой объект:
		final Gson gson = new Gson();
		// Настраиваем, куда будем выводить сериализованные данные
		try(JsonReader jr = new JsonReader(new FileReader(file)))
		{
			// Запускаем процесс десериализации
			final ClassToSave testObject = gson.fromJson(jr, ClassToSave.class);
			// Готово!
			BukkitPluginMain.consoleLog.log(Level.INFO,
				"[rbtmpl] [gson-example] Object read, caption: {0}", testObject.caption);
		} catch(JsonIOException | JsonSyntaxException | IOException ex) {
		}
	}
}
