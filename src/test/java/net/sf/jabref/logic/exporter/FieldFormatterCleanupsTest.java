begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
operator|.
name|FieldFormatterCleanup
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|IdentityFormatter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeDateFormatter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizePagesFormatter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|casechanger
operator|.
name|LowerCaseFormatter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexEntryTypes
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|FieldFormatterCleanupsTest
specifier|public
class|class
name|FieldFormatterCleanupsTest
block|{
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|BeforeClass
DECL|method|setUpPreferences ()
specifier|public
specifier|static
name|void
name|setUpPreferences
parameter_list|()
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|==
literal|null
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entry
operator|.
name|setType
argument_list|(
name|BibtexEntryTypes
operator|.
name|INPROCEEDINGS
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"6055279"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Educational session 1"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
literal|"Custom Integrated Circuits Conference (CICC), 2011 IEEE"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2011"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"mont"
argument_list|,
literal|"Sept."
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"1-7"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
literal|"Start of the above-titled section of the conference proceedings record."
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1109/CICC.2011.6055279"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
literal|"0886-5930"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|checkSimpleUseCase ()
specifier|public
name|void
name|checkSimpleUseCase
parameter_list|()
block|{
name|FieldFormatterCleanups
name|actions
init|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
literal|"title[identity]"
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|identityInTitle
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|IdentityFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|identityInTitle
argument_list|)
argument_list|,
name|actions
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
expr_stmt|;
name|actions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Educational session 1"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|invalidSaveActionSting ()
specifier|public
name|void
name|invalidSaveActionSting
parameter_list|()
block|{
name|FieldFormatterCleanups
name|actions
init|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
literal|"title"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|actions
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
expr_stmt|;
name|actions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Educational session 1"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|checkLowerCaseSaveAction ()
specifier|public
name|void
name|checkLowerCaseSaveAction
parameter_list|()
block|{
name|FieldFormatterCleanups
name|actions
init|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
literal|"title[lower_case]"
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|lowerCaseTitle
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|LowerCaseFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|lowerCaseTitle
argument_list|)
argument_list|,
name|actions
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
expr_stmt|;
name|actions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"educational session 1"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|checkTwoSaveActionsForOneField ()
specifier|public
name|void
name|checkTwoSaveActionsForOneField
parameter_list|()
block|{
name|FieldFormatterCleanups
name|actions
init|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
literal|"title[lower_case,identity]"
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|lowerCaseTitle
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|LowerCaseFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|identityInTitle
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|IdentityFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|lowerCaseTitle
argument_list|,
name|identityInTitle
argument_list|)
argument_list|,
name|actions
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
expr_stmt|;
name|actions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"educational session 1"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|checkThreeSaveActionsForOneField ()
specifier|public
name|void
name|checkThreeSaveActionsForOneField
parameter_list|()
block|{
name|FieldFormatterCleanups
name|actions
init|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
literal|"title[lower_case,identity,normalize_date]"
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|lowerCaseTitle
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|LowerCaseFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|identityInTitle
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|IdentityFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|normalizeDatesInTitle
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|NormalizeDateFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|lowerCaseTitle
argument_list|,
name|identityInTitle
argument_list|,
name|normalizeDatesInTitle
argument_list|)
argument_list|,
name|actions
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
expr_stmt|;
name|actions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"educational session 1"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|checkMultipleSaveActions ()
specifier|public
name|void
name|checkMultipleSaveActions
parameter_list|()
block|{
name|FieldFormatterCleanups
name|actions
init|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
literal|"pages[normalize_page_numbers]title[lower_case]"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|formatterCleanups
init|=
name|actions
operator|.
name|getConfiguredActions
argument_list|()
decl_stmt|;
name|FieldFormatterCleanup
name|normalizePages
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"pages"
argument_list|,
operator|new
name|NormalizePagesFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|lowerCaseTitle
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|LowerCaseFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|normalizePages
argument_list|,
name|lowerCaseTitle
argument_list|)
argument_list|,
name|formatterCleanups
argument_list|)
expr_stmt|;
name|actions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"educational session 1"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1--7"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|checkMultipleSaveActionsWithMultipleFormatters ()
specifier|public
name|void
name|checkMultipleSaveActionsWithMultipleFormatters
parameter_list|()
block|{
name|FieldFormatterCleanups
name|actions
init|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
literal|"pages[normalize_page_numbers,normalize_date]title[lower_case]"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|formatterCleanups
init|=
name|actions
operator|.
name|getConfiguredActions
argument_list|()
decl_stmt|;
name|FieldFormatterCleanup
name|normalizePages
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"pages"
argument_list|,
operator|new
name|NormalizePagesFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|normalizeDatesInPages
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"pages"
argument_list|,
operator|new
name|NormalizeDateFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|FieldFormatterCleanup
name|lowerCaseTitle
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|LowerCaseFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|normalizePages
argument_list|,
name|normalizeDatesInPages
argument_list|,
name|lowerCaseTitle
argument_list|)
argument_list|,
name|formatterCleanups
argument_list|)
expr_stmt|;
name|actions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"educational session 1"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1--7"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|clearFormatterRemovesField ()
specifier|public
name|void
name|clearFormatterRemovesField
parameter_list|()
block|{
name|FieldFormatterCleanups
name|actions
init|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
literal|"mont[clear]"
argument_list|)
decl_stmt|;
name|actions
operator|.
name|applySaveActions
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"mont"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

