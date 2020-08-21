package se206.a2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class GameModelDataSource {
    private final String _categoriesDir;

    public GameModelDataSource(String categoriesDir) {
        _categoriesDir = categoriesDir;
    }

    public List<Category> loadCategories() {
        // open categories directory
        File categoriesDir = new File(_categoriesDir);
        if (!categoriesDir.exists() || !categoriesDir.isDirectory()) {
            return new ArrayList<>();
        }

        // read each category from the directory
        List<Category> categories = new ArrayList<>();
        for (String categoryName : Objects.requireNonNull(categoriesDir.list())) {
            Category category = new Category(categoryName);
            categories.add(category);

            // parse the category file into questions
            try (Stream<String> stream = Files.lines(Paths.get(_categoriesDir, categoryName))) {
                stream.forEach(line -> {
                    String[] split = line.split(",");
                    try {
                        int value = Integer.parseInt(split[0]);
                        category.addQuestion(new Question(category, value, split[1], split[2]));
                    }
                    catch (NumberFormatException e) {
                        //
                    }
                });
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }
}
