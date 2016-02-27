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
name|List
import|;
end_import

begin_class
DECL|class|BibtexEntryTests
specifier|public
class|class
name|BibtexEntryTests
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
DECL|method|isNullOrEmptyCiteKey ()
specifier|public
name|void
name|isNullOrEmptyCiteKey
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
name|setField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
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
try|try
block|{
name|e
operator|.
name|setField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|asExpected
parameter_list|)
block|{          }
name|e
operator|.
name|setField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
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
literal|"2003-02"
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
literal|"2003-03"
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
literal|"2003"
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
literal|null
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
literal|null
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
literal|"2003-12"
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {03}, month = #DEC# }"
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
DECL|method|testKeywordMethods ()
specifier|public
name|void
name|testKeywordMethods
parameter_list|()
block|{
name|BibEntry
name|be
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{Key15, keywords = {Foo, Bar}}"
argument_list|)
decl_stmt|;
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
name|be
operator|.
name|getSeparatedKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|kw
init|=
name|be
operator|.
name|getSeparatedKeywords
argument_list|()
decl_stmt|;
name|be
operator|.
name|addKeyword
argument_list|(
literal|"FooBar"
argument_list|)
expr_stmt|;
name|String
index|[]
name|expected2
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
name|expected2
argument_list|,
name|be
operator|.
name|getSeparatedKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|addKeyword
argument_list|(
literal|"FooBar"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected2
argument_list|,
name|be
operator|.
name|getSeparatedKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|addKeyword
argument_list|(
literal|"FOO"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected2
argument_list|,
name|be
operator|.
name|getSeparatedKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|addKeyword
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected2
argument_list|,
name|be
operator|.
name|getSeparatedKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|be
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
catch|catch
parameter_list|(
name|NullPointerException
name|asExpected
parameter_list|)
block|{          }
name|BibEntry
name|be2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|be2
operator|.
name|getSeparatedKeywords
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|be2
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
name|be2
operator|.
name|getSeparatedKeywords
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|be2
operator|.
name|addKeywords
argument_list|(
name|be
operator|.
name|getSeparatedKeywords
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected2
argument_list|,
name|be2
operator|.
name|getSeparatedKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
name|be2
operator|.
name|putKeywords
argument_list|(
name|kw
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|be2
operator|.
name|getSeparatedKeywords
argument_list|()
operator|.
name|toArray
argument_list|()
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
name|setField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
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

