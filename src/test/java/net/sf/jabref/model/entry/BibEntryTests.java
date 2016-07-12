begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

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
name|JabRefPreferences
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexParser
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
name|FieldChange
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
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

begin_class
DECL|class|BibEntryTests
specifier|public
class|class
name|BibEntryTests
block|{
DECL|field|keywordEntry
specifier|private
name|BibEntry
name|keywordEntry
decl_stmt|;
DECL|field|emptyEntry
specifier|private
name|BibEntry
name|emptyEntry
decl_stmt|;
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
comment|// Default entry for most keyword and some type tests
name|keywordEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|keywordEntry
operator|.
name|setType
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|keywordEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Foo, Bar"
argument_list|)
expr_stmt|;
name|keywordEntry
operator|.
name|setChanged
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// Empty entry for some tests
name|emptyEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|emptyEntry
operator|.
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|emptyEntry
operator|.
name|setChanged
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDefaultConstructor ()
specifier|public
name|void
name|testDefaultConstructor
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
comment|// we have to use `getType("misc")` in the case of biblatex mode
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"misc"
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|allFieldsPresentDefault ()
specifier|public
name|void
name|allFieldsPresentDefault
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
literal|"id"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"abc"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"abc"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"abc"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|requiredFields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|requiredFields
operator|.
name|add
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|requiredFields
operator|.
name|add
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|e
operator|.
name|allFieldsPresent
argument_list|(
name|requiredFields
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|requiredFields
operator|.
name|add
argument_list|(
literal|"year"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|e
operator|.
name|allFieldsPresent
argument_list|(
name|requiredFields
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|allFieldsPresentOr ()
specifier|public
name|void
name|allFieldsPresentOr
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
literal|"id"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"abc"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"abc"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"abc"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|requiredFields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// XOR required
name|requiredFields
operator|.
name|add
argument_list|(
literal|"journal/year"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|e
operator|.
name|allFieldsPresent
argument_list|(
name|requiredFields
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|requiredFields
operator|.
name|add
argument_list|(
literal|"year/address"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|e
operator|.
name|allFieldsPresent
argument_list|(
name|requiredFields
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|isNullCiteKeyThrowsNPE ()
specifier|public
name|void
name|isNullCiteKeyThrowsNPE
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
literal|"id"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|e
operator|.
name|setCiteKey
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isEmptyCiteKey ()
specifier|public
name|void
name|isEmptyCiteKey
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
literal|"id"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|e
operator|.
name|hasCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|setCiteKey
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|e
operator|.
name|hasCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|setCiteKey
argument_list|(
literal|"key"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|e
operator|.
name|hasCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|clearField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|e
operator|.
name|hasCiteKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|typeOfBibEntryIsMiscAfterSettingToNullString ()
specifier|public
name|void
name|typeOfBibEntryIsMiscAfterSettingToNullString
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"article"
argument_list|,
name|keywordEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|keywordEntry
operator|.
name|setType
argument_list|(
operator|(
name|String
operator|)
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"misc"
argument_list|,
name|keywordEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|typeOfBibEntryIsMiscAfterSettingToEmptyString ()
specifier|public
name|void
name|typeOfBibEntryIsMiscAfterSettingToEmptyString
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"article"
argument_list|,
name|keywordEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|keywordEntry
operator|.
name|setType
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"misc"
argument_list|,
name|keywordEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetPublicationDate ()
specifier|public
name|void
name|testGetPublicationDate
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2003-02"
argument_list|)
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = #FEB# }"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2003-03"
argument_list|)
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = 3 }"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2003"
argument_list|)
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}}"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, month = 3 }"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, author={bla}}"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2003-12"
argument_list|)
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = #DEC# }"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasDateWithYearNumericalMonthString ()
specifier|public
name|void
name|getFieldOrAliasDateWithYearNumericalMonthString
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2003"
argument_list|)
expr_stmt|;
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"3"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003-03"
argument_list|,
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"date"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasDateWithYearAbbreviatedMonth ()
specifier|public
name|void
name|getFieldOrAliasDateWithYearAbbreviatedMonth
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2003"
argument_list|)
expr_stmt|;
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"#mar#"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003-03"
argument_list|,
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"date"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasDateWithYearAbbreviatedMonthString ()
specifier|public
name|void
name|getFieldOrAliasDateWithYearAbbreviatedMonthString
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2003"
argument_list|)
expr_stmt|;
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"mar"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003-03"
argument_list|,
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"date"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasDateWithOnlyYear ()
specifier|public
name|void
name|getFieldOrAliasDateWithOnlyYear
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2003"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003"
argument_list|,
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"date"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasYearWithDateYYYY ()
specifier|public
name|void
name|getFieldOrAliasYearWithDateYYYY
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"2003"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003"
argument_list|,
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasYearWithDateYYYYMM ()
specifier|public
name|void
name|getFieldOrAliasYearWithDateYYYYMM
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"2003-03"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003"
argument_list|,
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasYearWithDateYYYYMMDD ()
specifier|public
name|void
name|getFieldOrAliasYearWithDateYYYYMMDD
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"2003-03-30"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003"
argument_list|,
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasMonthWithDateYYYYReturnsNull ()
specifier|public
name|void
name|getFieldOrAliasMonthWithDateYYYYReturnsNull
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"2003"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasMonthWithDateYYYYMM ()
specifier|public
name|void
name|getFieldOrAliasMonthWithDateYYYYMM
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"2003-03"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"3"
argument_list|,
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldOrAliasMonthWithDateYYYYMMDD ()
specifier|public
name|void
name|getFieldOrAliasMonthWithDateYYYYMMDD
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"2003-03-30"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"3"
argument_list|,
name|emptyEntry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|setNullField ()
specifier|public
name|void
name|setNullField
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|addNullKeywordThrowsNPE ()
specifier|public
name|void
name|addNullKeywordThrowsNPE
parameter_list|()
block|{
name|keywordEntry
operator|.
name|addKeyword
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|putNullKeywordListThrowsNPE ()
specifier|public
name|void
name|putNullKeywordListThrowsNPE
parameter_list|()
block|{
name|keywordEntry
operator|.
name|putKeywords
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSeparatedKeywordsAreCorrect ()
specifier|public
name|void
name|testGetSeparatedKeywordsAreCorrect
parameter_list|()
block|{
name|String
index|[]
name|expected
init|=
block|{
literal|"Foo"
block|,
literal|"Bar"
block|}
decl_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|keywordEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAddKeywordIsCorrect ()
specifier|public
name|void
name|testAddKeywordIsCorrect
parameter_list|()
block|{
name|keywordEntry
operator|.
name|addKeyword
argument_list|(
literal|"FooBar"
argument_list|)
expr_stmt|;
name|String
index|[]
name|expected
init|=
block|{
literal|"Foo"
block|,
literal|"Bar"
block|,
literal|"FooBar"
block|}
decl_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|keywordEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAddKeywordHasChanged ()
specifier|public
name|void
name|testAddKeywordHasChanged
parameter_list|()
block|{
name|keywordEntry
operator|.
name|addKeyword
argument_list|(
literal|"FooBar"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|keywordEntry
operator|.
name|hasChanged
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAddKeywordTwiceYiedsOnlyOne ()
specifier|public
name|void
name|testAddKeywordTwiceYiedsOnlyOne
parameter_list|()
block|{
name|keywordEntry
operator|.
name|addKeyword
argument_list|(
literal|"FooBar"
argument_list|)
expr_stmt|;
name|keywordEntry
operator|.
name|addKeyword
argument_list|(
literal|"FooBar"
argument_list|)
expr_stmt|;
name|String
index|[]
name|expected
init|=
block|{
literal|"Foo"
block|,
literal|"Bar"
block|,
literal|"FooBar"
block|}
decl_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|keywordEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addKeywordIsCaseSensitive ()
specifier|public
name|void
name|addKeywordIsCaseSensitive
parameter_list|()
block|{
name|keywordEntry
operator|.
name|addKeyword
argument_list|(
literal|"FOO"
argument_list|)
expr_stmt|;
name|String
index|[]
name|expected
init|=
block|{
literal|"Foo"
block|,
literal|"Bar"
block|,
literal|"FOO"
block|}
decl_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|keywordEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAddKeywordWithDifferentCapitalizationChanges ()
specifier|public
name|void
name|testAddKeywordWithDifferentCapitalizationChanges
parameter_list|()
block|{
name|keywordEntry
operator|.
name|addKeyword
argument_list|(
literal|"FOO"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|keywordEntry
operator|.
name|hasChanged
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAddKeywordEmptyKeywordIsNotAdded ()
specifier|public
name|void
name|testAddKeywordEmptyKeywordIsNotAdded
parameter_list|()
block|{
name|keywordEntry
operator|.
name|addKeyword
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|String
index|[]
name|expected
init|=
block|{
literal|"Foo"
block|,
literal|"Bar"
block|}
decl_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|keywordEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAddKeywordEmptyKeywordNotChanged ()
specifier|public
name|void
name|testAddKeywordEmptyKeywordNotChanged
parameter_list|()
block|{
name|keywordEntry
operator|.
name|addKeyword
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|keywordEntry
operator|.
name|hasChanged
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|texNewBibEntryHasNoKeywords ()
specifier|public
name|void
name|texNewBibEntryHasNoKeywords
parameter_list|()
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|emptyEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|texNewBibEntryHasNoKeywordsEvenAfterAddingEmptyKeyword ()
specifier|public
name|void
name|texNewBibEntryHasNoKeywordsEvenAfterAddingEmptyKeyword
parameter_list|()
block|{
name|emptyEntry
operator|.
name|addKeyword
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|emptyEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|texNewBibEntryAfterAddingEmptyKeywordNotChanged ()
specifier|public
name|void
name|texNewBibEntryAfterAddingEmptyKeywordNotChanged
parameter_list|()
block|{
name|emptyEntry
operator|.
name|addKeyword
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|emptyEntry
operator|.
name|hasChanged
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAddKeywordsWorksAsExpected ()
specifier|public
name|void
name|testAddKeywordsWorksAsExpected
parameter_list|()
block|{
name|String
index|[]
name|expected
init|=
block|{
literal|"Foo"
block|,
literal|"Bar"
block|}
decl_stmt|;
name|emptyEntry
operator|.
name|addKeywords
argument_list|(
name|keywordEntry
operator|.
name|getKeywords
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|emptyEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPutKeywordsOverwritesOldKeywords ()
specifier|public
name|void
name|testPutKeywordsOverwritesOldKeywords
parameter_list|()
block|{
name|keywordEntry
operator|.
name|putKeywords
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Yin"
argument_list|,
literal|"Yang"
argument_list|)
argument_list|)
expr_stmt|;
name|String
index|[]
name|expected
init|=
block|{
literal|"Yin"
block|,
literal|"Yang"
block|}
decl_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|keywordEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPutKeywordsHasChanged ()
specifier|public
name|void
name|testPutKeywordsHasChanged
parameter_list|()
block|{
name|keywordEntry
operator|.
name|putKeywords
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Yin"
argument_list|,
literal|"Yang"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|keywordEntry
operator|.
name|hasChanged
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPutKeywordsPutEmpyListErasesPreviousKeywords ()
specifier|public
name|void
name|testPutKeywordsPutEmpyListErasesPreviousKeywords
parameter_list|()
block|{
name|keywordEntry
operator|.
name|putKeywords
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|keywordEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPutKeywordsPutEmpyListHasChanged ()
specifier|public
name|void
name|testPutKeywordsPutEmpyListHasChanged
parameter_list|()
block|{
name|keywordEntry
operator|.
name|putKeywords
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|keywordEntry
operator|.
name|hasChanged
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPutKeywordsPutEmpyListToEmptyBibentry ()
specifier|public
name|void
name|testPutKeywordsPutEmpyListToEmptyBibentry
parameter_list|()
block|{
name|emptyEntry
operator|.
name|putKeywords
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|emptyEntry
operator|.
name|getKeywords
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPutKeywordsPutEmpyListToEmptyBibentryNotChanged ()
specifier|public
name|void
name|testPutKeywordsPutEmpyListToEmptyBibentryNotChanged
parameter_list|()
block|{
name|emptyEntry
operator|.
name|putKeywords
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|emptyEntry
operator|.
name|hasChanged
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|putKeywordsToEmptyReturnsNoChange ()
specifier|public
name|void
name|putKeywordsToEmptyReturnsNoChange
parameter_list|()
block|{
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|change
init|=
name|emptyEntry
operator|.
name|putKeywords
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|change
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|clearKeywordsReturnsChange ()
specifier|public
name|void
name|clearKeywordsReturnsChange
parameter_list|()
block|{
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|change
init|=
name|keywordEntry
operator|.
name|putKeywords
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|keywordEntry
argument_list|,
literal|"keywords"
argument_list|,
literal|"Foo, Bar"
argument_list|,
literal|null
argument_list|)
argument_list|)
argument_list|,
name|change
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|changeKeywordsReturnsChange ()
specifier|public
name|void
name|changeKeywordsReturnsChange
parameter_list|()
block|{
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|change
init|=
name|keywordEntry
operator|.
name|putKeywords
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Test"
argument_list|,
literal|"FooTest"
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|keywordEntry
argument_list|,
literal|"keywords"
argument_list|,
literal|"Foo, Bar"
argument_list|,
literal|"Test, FooTest"
argument_list|)
argument_list|)
argument_list|,
name|change
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|putKeywordsToSameReturnsNoChange ()
specifier|public
name|void
name|putKeywordsToSameReturnsNoChange
parameter_list|()
block|{
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|change
init|=
name|keywordEntry
operator|.
name|putKeywords
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Foo"
argument_list|,
literal|"Bar"
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|change
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGroupAndSearchHits ()
specifier|public
name|void
name|testGroupAndSearchHits
parameter_list|()
block|{
name|BibEntry
name|be
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|be
operator|.
name|setGroupHit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|be
operator|.
name|isGroupHit
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|setGroupHit
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|be
operator|.
name|isGroupHit
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|setSearchHit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|be
operator|.
name|isSearchHit
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|setSearchHit
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|be
operator|.
name|isSearchHit
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCiteKeyAndID ()
specifier|public
name|void
name|testCiteKeyAndID
parameter_list|()
block|{
name|BibEntry
name|be
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|be
operator|.
name|hasCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Albert Einstein"
argument_list|)
expr_stmt|;
name|be
operator|.
name|setCiteKey
argument_list|(
literal|"Einstein1931"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|be
operator|.
name|hasCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Einstein1931"
argument_list|,
name|be
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Albert Einstein"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|be
operator|.
name|clearField
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|be
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|id
init|=
name|IdGenerator
operator|.
name|next
argument_list|()
decl_stmt|;
name|be
operator|.
name|setId
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|id
argument_list|,
name|be
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

