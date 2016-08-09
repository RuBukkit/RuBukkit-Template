package org.rubukkit.template.API;

public class Example_SaveLoadJSON_Fields
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
		public InternalThing(String text)
		{
			this.text = text;
		}
	}
	public InternalThing[] things;
	// Ключевым словом transient помечается то, что никогда не будет сериализовано
	public transient int noNoNoPlease;
	// Если поле равно null, оно будет пропущено при сериализизации
}
