package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataLoaderService {
    private static final String CATEGORIES_FILE = "/data/categories.csv";
    private static final String TIPS_FILE = "/data/recycling_tips.csv";
    private static final String GUIDELINES_FILE = "/data/disposal_guidelines.csv";

    private final WasteCategoryService categoryService;
    private final RecyclingTipService tipService;
    private final DisposalGuidelineService guidelineService;
    private final Map<String, WasteCategory> categoryMap = new HashMap<>();


    /**
     * Creates a new DataLoaderService with required services.
     * @param categoryService service for managing waste categories
     * @param tipService service for managing recycling tips
     * @param guidelineService service for managing disposal guidelines
     */
    public DataLoaderService(WasteCategoryService categoryService,
                      RecyclingTipService tipService,
                      DisposalGuidelineService guidelineService) {
        this.categoryService = categoryService;
        this.tipService = tipService;
        this.guidelineService = guidelineService;
    }

    /**
     * Initializes the database with data from CSV files.
     * @throws  RuntimeException if data loading fails.
     */
    @PostConstruct
    public void loadData() {
        try {
            loadCategories();
            loadRecyclingTips();
            loadDisposalGuidelines();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV files: " + e.getMessage(), e);
        } catch (CsvException e) {
            throw new RuntimeException("Failed to parse CSV data: " + e.getMessage(), e);
        }
    }

    /**
     * Creates a CSV reader for the given file.
     * @param filePath path to the CSV file
     * @return CSVReader instance for the file
     * @throws IOException if file cannot be read
     */
    private CSVReader createReader(String filePath) throws IOException {
        var inputStream = getClass().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
        }
        return new CSVReader(new InputStreamReader(inputStream));
    }

    /**
     * Checks if CSV line has correct number of elements.
     * @param line CSV line elements
     * @param expectedLength expected number of elements
     * @param errorMessage error message if validation fails
     * @throws CsvException if line length is invalid
     */
    private void validateLineLength(String[] line, int expectedLength, String errorMessage)
            throws CsvException {
        if (line.length < expectedLength) {
            throw new CsvException(errorMessage);
        }
    }

    /**
     * Finds a category by name.
     * @param categoryName name of the category
     * @return matching WasteCategory
     * @throws CsvException if category not found
     */
    private WasteCategory findCategory(String categoryName) throws CsvException {
        WasteCategory category = categoryMap.get(categoryName.trim());
        if (category == null) {
            throw new CsvException("Category not found: " + categoryName);
        }
        return category;
    }

    /**
     * Loads waste categories from CSV.
     * @throws IOException if file reading fails
     * @throws CsvException if CSV parsing fails
     */
    private void loadCategories() throws IOException, CsvException {
        try (CSVReader csvReader = createReader(CATEGORIES_FILE)) {
            csvReader.readNext();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                processCategory(line);
            }
        }
    }

    /**
     * Processes one category from CSV line.
     * @param line CSV line containing category data
     * @throws CsvException if data is invalid
     */
    private void processCategory(String[] line) throws CsvException {
        validateLineLength(line, 2,
                "Invalid category data format: requires name and description");
        try {
            WasteCategory category = new WasteCategory(
                    null,
                    line[0].trim(),
                    line[1].trim()
            );
            WasteCategory saved = categoryService.createCategory(category);
            categoryMap.put(saved.getName(), saved);
        } catch (IllegalArgumentException e) {
            throw new CsvException("Invalid category data: " + e.getMessage());
        }
    }

    /**
     * Loads recycling tips from CSV.
     * @throws IOException if file reading fails
     * @throws CsvException if CSV parsing fails
     */
    private void loadRecyclingTips() throws IOException, CsvException {
        try (CSVReader csvReader = createReader(TIPS_FILE)) {
            csvReader.readNext();
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                processTip(line);
            }
        }
    }

    /**
     * Processes one recycling tip from CSV line.
     * @param line CSV line containing tip data
     * @throws CsvException if data is invalid
     */
    private void processTip(String[] line) throws CsvException {
        validateLineLength(line, 3,
                "Invalid tip data format: requires title, content, and category");
        WasteCategory category = findCategory(line[2]);
        try {
            RecyclingTip tip = new RecyclingTip(
                    null,
                    line[0].trim(),
                    line[1].trim(),
                    category
            );
            tipService.createTip(category.getId(), tip);
        } catch (IllegalArgumentException e) {
            throw new CsvException("Invalid tip data: " + e.getMessage());
        }
    }

    /**
     * Loads disposal guidelines from CSV.
     * @throws IOException if file reading fails
     * @throws CsvException if CSV parsing fails
     */
    private void loadDisposalGuidelines() throws IOException, CsvException {
        try (CSVReader csvReader = createReader(GUIDELINES_FILE)) {
            csvReader.readNext();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                processGuideline(line);
            }
        }
    }

    /**
     * Processes one disposal guideline from CSV line.
     * @param line CSV line containing guideline data
     * @throws CsvException if data is invalid
     */
    private void processGuideline(String[] line) throws CsvException {
        validateLineLength(line, 3,
                "Invalid guideline data format: requires title, instructions, and category");
        WasteCategory category = findCategory(line[2]);
        try {
            DisposalGuideline guideline = new DisposalGuideline(
                    null,
                    line[0].trim(),
                    line[1].trim(),
                    category
            );
            guidelineService.createGuideline(category.getId(), guideline);
        } catch (IllegalArgumentException e) {
            throw new CsvException("Invalid guideline data: " + e.getMessage());
        }
    }
}