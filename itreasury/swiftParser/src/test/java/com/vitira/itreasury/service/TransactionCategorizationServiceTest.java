package com.vitira.itreasury.service;

import com.vitira.itreasury.entity.Transaction;
import com.vitira.itreasury.entity.TransactionCategory;
import com.vitira.itreasury.enums.CategoryType;
import com.vitira.itreasury.repository.TransactionCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionCategorizationServiceTest {

    @Mock
    private TransactionCategoryRepository categoryRepository;

    @InjectMocks
    private TransactionCategorizationService categorizationService;

    private TransactionCategory foreignInflowCategory;
    private TransactionCategory normalInflowCategory;
    private TransactionCategory foreignOutflowCategory;
    private TransactionCategory normalOutflowCategory;
    private TransactionCategory defaultInflowCategory;
    private TransactionCategory defaultOutflowCategory;

    @BeforeEach
    void setUp() {
        // Setup test categories
        foreignInflowCategory = TransactionCategory.builder()
                .id(1L)
                .categoryType(CategoryType.INFLOW)
                .categoryName("Foreign Inflow")
                .identificationCodes("ID1,ID2")
                .transactionTypeCodes("F")
                .descKeywords("foreign,import")
                .build();

        normalInflowCategory = TransactionCategory.builder()
                .id(2L)
                .categoryType(CategoryType.INFLOW)
                .categoryName("Normal Inflow")
                .identificationCodes("ID3,ID4")
                .transactionTypeCodes("N")
                .descKeywords("local,sale")
                .build();

        foreignOutflowCategory = TransactionCategory.builder()
                .id(3L)
                .categoryType(CategoryType.OUTFLOW)
                .categoryName("Foreign Outflow")
                .identificationCodes("ID5,ID6")
                .transactionTypeCodes("F")
                .descKeywords("foreign,export")
                .build();

        normalOutflowCategory = TransactionCategory.builder()
                .id(4L)
                .categoryType(CategoryType.OUTFLOW)
                .categoryName("Normal Outflow")
                .identificationCodes("ID7,ID8")
                .transactionTypeCodes("N")
                .descKeywords("local,purchase")
                .build();

        defaultInflowCategory = TransactionCategory.builder()
                .id(5L)
                .categoryType(CategoryType.INFLOW)
                .categoryName("Miscellaneous")
                .identificationCodes("")
                .transactionTypeCodes("")
                .descKeywords("")
                .build();

        defaultOutflowCategory = TransactionCategory.builder()
                .id(6L)
                .categoryType(CategoryType.OUTFLOW)
                .categoryName("Miscellaneous")
                .identificationCodes("")
                .transactionTypeCodes("")
                .descKeywords("")
                .build();
    }

    @Test
    @DisplayName("Should categorize foreign inflow transaction correctly")
    void testCategorizeForeignInflowTransaction() {
        // Given
        Transaction transaction = createTestTransaction("C", "ID1", "F", "This is a foreign import transaction");
        List<TransactionCategory> categories = Arrays.asList(
                foreignInflowCategory, normalInflowCategory, 
                foreignOutflowCategory, normalOutflowCategory
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findByCategoryTypeAndCategoryName(CategoryType.INFLOW, "Miscellaneous"))
                .thenReturn(defaultInflowCategory);

        // When
        Transaction result = categorizationService.categorizeTransaction(transaction);

        // Then
        assertNotNull(result);
        assertEquals(foreignInflowCategory, result.getCategory());
        verify(categoryRepository).findAll();
        verify(categoryRepository, never()).findByCategoryTypeAndCategoryName(any(), any());
    }

    @Test
    @DisplayName("Should categorize normal inflow transaction correctly")
    void testCategorizeNormalInflowTransaction() {
        // Given
        Transaction transaction = createTestTransaction("C", "ID3", "N", "This is a local sale transaction");
        List<TransactionCategory> categories = Arrays.asList(
                foreignInflowCategory, normalInflowCategory, 
                foreignOutflowCategory, normalOutflowCategory
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findByCategoryTypeAndCategoryName(CategoryType.INFLOW, "Miscellaneous"))
                .thenReturn(defaultInflowCategory);

        // When
        Transaction result = categorizationService.categorizeTransaction(transaction);

        // Then
        assertNotNull(result);
        assertEquals(normalInflowCategory, result.getCategory());
    }

    @Test
    @DisplayName("Should categorize foreign outflow transaction correctly")
    void testCategorizeForeignOutflowTransaction() {
        // Given
        Transaction transaction = createTestTransaction("D", "ID5", "F", "This is a foreign export transaction");
        List<TransactionCategory> categories = Arrays.asList(
                foreignInflowCategory, normalInflowCategory, 
                foreignOutflowCategory, normalOutflowCategory
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findByCategoryTypeAndCategoryName(CategoryType.OUTFLOW, "Miscellaneous"))
                .thenReturn(defaultOutflowCategory);

        // When
        Transaction result = categorizationService.categorizeTransaction(transaction);

        // Then
        assertNotNull(result);
        assertEquals(foreignOutflowCategory, result.getCategory());
    }

    @Test
    @DisplayName("Should categorize normal outflow transaction correctly")
    void testCategorizeNormalOutflowTransaction() {
        // Given
        Transaction transaction = createTestTransaction("D", "ID7", "N", "This is a local purchase transaction");
        List<TransactionCategory> categories = Arrays.asList(
                foreignInflowCategory, normalInflowCategory, 
                foreignOutflowCategory, normalOutflowCategory
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findByCategoryTypeAndCategoryName(CategoryType.OUTFLOW, "Miscellaneous"))
                .thenReturn(defaultOutflowCategory);

        // When
        Transaction result = categorizationService.categorizeTransaction(transaction);

        // Then
        assertNotNull(result);
        assertEquals(normalOutflowCategory, result.getCategory());
    }

    @Test
    @DisplayName("Should assign default category when no matching category found")
    void testCategorizeTransactionWithNoMatchingCategory() {
        // Given
        Transaction transaction = createTestTransaction("C", "UNKNOWN", "F", "Unknown transaction");
        List<TransactionCategory> categories = Arrays.asList(
                foreignInflowCategory, normalInflowCategory, 
                foreignOutflowCategory, normalOutflowCategory
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findByCategoryTypeAndCategoryName(CategoryType.INFLOW, "Miscellaneous"))
                .thenReturn(defaultInflowCategory);

        // When
        Transaction result = categorizationService.categorizeTransaction(transaction);

        // Then
        assertNotNull(result);
        assertEquals(defaultInflowCategory, result.getCategory());
    }

    @Test
    @DisplayName("Should assign default category when ID matches but type doesn't")
    void testCategorizeTransactionWithMatchingIdButDifferentType() {
        // Given
        Transaction transaction = createTestTransaction("C", "ID1", "N", "This is a transaction with matching ID but different type");
        List<TransactionCategory> categories = Arrays.asList(
                foreignInflowCategory, normalInflowCategory, 
                foreignOutflowCategory, normalOutflowCategory
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findByCategoryTypeAndCategoryName(CategoryType.INFLOW, "Miscellaneous"))
                .thenReturn(defaultInflowCategory);

        // When
        Transaction result = categorizationService.categorizeTransaction(transaction);

        // Then
        assertNotNull(result);
        assertEquals(defaultInflowCategory, result.getCategory());
    }

    @Test
    @DisplayName("Should assign default category when ID and type match but description doesn't")
    void testCategorizeTransactionWithMatchingIdAndTypeButNoDescriptionMatch() {
        // Given
        Transaction transaction = createTestTransaction("C", "ID1", "F", "This description doesn't match any keywords");
        List<TransactionCategory> categories = Arrays.asList(
                foreignInflowCategory, normalInflowCategory, 
                foreignOutflowCategory, normalOutflowCategory
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findByCategoryTypeAndCategoryName(CategoryType.INFLOW, "Miscellaneous"))
                .thenReturn(defaultInflowCategory);

        // When
        Transaction result = categorizationService.categorizeTransaction(transaction);

        // Then
        assertNotNull(result);
        assertEquals(defaultInflowCategory, result.getCategory());
    }

    private Transaction createTestTransaction(String debitCreditMark, String identificationCode, 
                                           String transactionTypeCode, String description) {
        return Transaction.builder()
                .debitCreditMark(debitCreditMark)
                .identificationCode(identificationCode)
                .transactionTypeCode(transactionTypeCode)
                .description(description)
                .valueDate(LocalDateTime.now())
                .entryDate(LocalDateTime.now())
                .amount(BigDecimal.valueOf(1000))
                .build();
    }
} 