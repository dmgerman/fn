begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|specialfields
package|;
end_package

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
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|SpecialFieldsUtilsTest
specifier|public
class|class
name|SpecialFieldsUtilsTest
block|{
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
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
annotation|@
name|Test
DECL|method|testSyncKeywordsFromSpecialFieldsBibEntry ()
specifier|public
name|void
name|testSyncKeywordsFromSpecialFieldsBibEntry
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"ranking"
argument_list|,
literal|"rank2"
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|syncKeywordsFromSpecialFields
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"rank2"
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"keywords"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSyncKeywordsFromSpecialFieldsBibEntryNamedCompoundHasEdits ()
specifier|public
name|void
name|testSyncKeywordsFromSpecialFieldsBibEntryNamedCompoundHasEdits
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|NamedCompound
name|nc
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"Test"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"ranking"
argument_list|,
literal|"rank2"
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|syncKeywordsFromSpecialFields
argument_list|(
name|entry
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|nc
operator|.
name|hasEdits
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSyncKeywordsFromSpecialFieldsBibEntryExisitingKeyword ()
specifier|public
name|void
name|testSyncKeywordsFromSpecialFieldsBibEntryExisitingKeyword
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"ranking"
argument_list|,
literal|"rank2"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"rank3"
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|syncKeywordsFromSpecialFields
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"rank2"
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"keywords"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSyncKeywordsFromSpecialFieldsBibEntryNamedCompoundCorrectContent ()
specifier|public
name|void
name|testSyncKeywordsFromSpecialFieldsBibEntryNamedCompoundCorrectContent
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|NamedCompound
name|nc
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"Test"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"ranking"
argument_list|,
literal|"rank2"
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|syncKeywordsFromSpecialFields
argument_list|(
name|entry
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"rank2"
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"keywords"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSyncKeywordsFromSpecialFieldsBibEntryNamedCompoundNoEdits ()
specifier|public
name|void
name|testSyncKeywordsFromSpecialFieldsBibEntryNamedCompoundNoEdits
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|NamedCompound
name|nc
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"Test"
argument_list|)
decl_stmt|;
name|SpecialFieldsUtils
operator|.
name|syncKeywordsFromSpecialFields
argument_list|(
name|entry
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|nc
operator|.
name|hasEdits
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSyncSpecialFieldsFromKeywordsBibEntry ()
specifier|public
name|void
name|testSyncSpecialFieldsFromKeywordsBibEntry
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"rank2"
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|syncSpecialFieldsFromKeywords
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"rank2"
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"ranking"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSyncSpecialFieldsFromKeywordsBibEntryNamedCompoundHasEdits ()
specifier|public
name|void
name|testSyncSpecialFieldsFromKeywordsBibEntryNamedCompoundHasEdits
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|NamedCompound
name|nc
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"Test"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"rank2"
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|syncSpecialFieldsFromKeywords
argument_list|(
name|entry
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|nc
operator|.
name|hasEdits
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSyncSpecialFieldsFromKeywordsBibEntryNamedCompoundCorrectContent ()
specifier|public
name|void
name|testSyncSpecialFieldsFromKeywordsBibEntryNamedCompoundCorrectContent
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|NamedCompound
name|nc
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"Test"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"rank2"
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|syncSpecialFieldsFromKeywords
argument_list|(
name|entry
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"rank2"
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"ranking"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSyncSpecialFieldsFromKeywordsBibEntryNamedCompoundNoEdit ()
specifier|public
name|void
name|testSyncSpecialFieldsFromKeywordsBibEntryNamedCompoundNoEdit
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|NamedCompound
name|nc
init|=
operator|new
name|NamedCompound
argument_list|(
literal|"Test"
argument_list|)
decl_stmt|;
name|SpecialFieldsUtils
operator|.
name|syncSpecialFieldsFromKeywords
argument_list|(
name|entry
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|nc
operator|.
name|hasEdits
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSpecialFieldInstanceFromFieldNameValid ()
specifier|public
name|void
name|testGetSpecialFieldInstanceFromFieldNameValid
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Rank
operator|.
name|getInstance
argument_list|()
argument_list|)
argument_list|,
name|SpecialFieldsUtils
operator|.
name|getSpecialFieldInstanceFromFieldName
argument_list|(
literal|"ranking"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSpecialFieldInstanceFromFieldNameInvalid ()
specifier|public
name|void
name|testGetSpecialFieldInstanceFromFieldNameInvalid
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|SpecialFieldsUtils
operator|.
name|getSpecialFieldInstanceFromFieldName
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsSpecialFieldTrue ()
specifier|public
name|void
name|testIsSpecialFieldTrue
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|SpecialFieldsUtils
operator|.
name|isSpecialField
argument_list|(
literal|"ranking"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsSpecialFieldFalse ()
specifier|public
name|void
name|testIsSpecialFieldFalse
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|SpecialFieldsUtils
operator|.
name|isSpecialField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

