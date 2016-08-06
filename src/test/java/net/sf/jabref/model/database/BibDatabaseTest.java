begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.database
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
name|importer
operator|.
name|ParserResult
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
name|BibtexString
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
name|IdGenerator
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
name|event
operator|.
name|TestEventListener
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
name|Rule
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
name|org
operator|.
name|junit
operator|.
name|rules
operator|.
name|ExpectedException
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
name|assertNull
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
import|;
end_import

begin_class
DECL|class|BibDatabaseTest
specifier|public
class|class
name|BibDatabaseTest
block|{
annotation|@
name|Rule
DECL|field|thrown
specifier|public
name|ExpectedException
name|thrown
init|=
name|ExpectedException
operator|.
name|none
argument_list|()
decl_stmt|;
DECL|field|database
specifier|private
name|BibDatabase
name|database
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
comment|// set preferences for this test
name|database
operator|=
operator|new
name|BibDatabase
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|resolveStrings ()
specifier|public
name|void
name|resolveStrings
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|FileInputStream
name|stream
init|=
operator|new
name|FileInputStream
argument_list|(
literal|"src/test/resources/net/sf/jabref/util/twente.bib"
argument_list|)
init|;
name|InputStreamReader
name|fr
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|stream
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|fr
argument_list|)
decl_stmt|;
name|BibDatabase
name|db
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Arvind"
argument_list|,
name|db
operator|.
name|resolveForStrings
argument_list|(
literal|"#Arvind#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Patterson, David"
argument_list|,
name|db
operator|.
name|resolveForStrings
argument_list|(
literal|"#Patterson#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Arvind and Patterson, David"
argument_list|,
name|db
operator|.
name|resolveForStrings
argument_list|(
literal|"#Arvind# and #Patterson#"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Strings that are not found return just the given string.
name|assertEquals
argument_list|(
literal|"#unknown#"
argument_list|,
name|db
operator|.
name|resolveForStrings
argument_list|(
literal|"#unknown#"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|insertEntryAddsEntryToEntriesList ()
specifier|public
name|void
name|insertEntryAddsEntryToEntriesList
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getEntryCount
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|entry
argument_list|,
name|database
operator|.
name|getEntries
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsEntryIdFindsEntry ()
specifier|public
name|void
name|containsEntryIdFindsEntry
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|assertFalse
argument_list|(
name|database
operator|.
name|containsEntryWithId
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|database
operator|.
name|containsEntryWithId
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|KeyCollisionException
operator|.
name|class
argument_list|)
DECL|method|insertEntryWithSameIdThrowsException ()
specifier|public
name|void
name|insertEntryWithSameIdThrowsException
parameter_list|()
block|{
name|BibEntry
name|entry0
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry0
argument_list|)
expr_stmt|;
name|BibEntry
name|entry1
init|=
operator|new
name|BibEntry
argument_list|(
name|entry0
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry1
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removeEntryRemovesEntryFromEntriesList ()
specifier|public
name|void
name|removeEntryRemovesEntryFromEntriesList
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|database
operator|.
name|removeEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|database
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|database
operator|.
name|containsEntryWithId
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
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
DECL|method|insertNullEntryThrowsException ()
specifier|public
name|void
name|insertNullEntryThrowsException
parameter_list|()
block|{
name|database
operator|.
name|insertEntry
argument_list|(
literal|null
argument_list|)
expr_stmt|;
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
DECL|method|removeNullEntryThrowsException ()
specifier|public
name|void
name|removeNullEntryThrowsException
parameter_list|()
block|{
name|database
operator|.
name|removeEntry
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|emptyDatabaseHasNoStrings ()
specifier|public
name|void
name|emptyDatabaseHasNoStrings
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptySet
argument_list|()
argument_list|,
name|database
operator|.
name|getStringKeySet
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|database
operator|.
name|hasNoStrings
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|insertStringUpdatesStringList ()
specifier|public
name|void
name|insertStringUpdatesStringList
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"DSP"
argument_list|,
literal|"Digital Signal Processing"
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|database
operator|.
name|hasNoStrings
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|size
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getStringCount
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|database
operator|.
name|getStringValues
argument_list|()
operator|.
name|contains
argument_list|(
name|string
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|database
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|contains
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|string
argument_list|,
name|database
operator|.
name|getString
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removeStringUpdatesStringList ()
specifier|public
name|void
name|removeStringUpdatesStringList
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"DSP"
argument_list|,
literal|"Digital Signal Processing"
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|database
operator|.
name|removeString
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|database
operator|.
name|hasNoStrings
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|size
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getStringCount
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|database
operator|.
name|getStringValues
argument_list|()
operator|.
name|contains
argument_list|(
name|string
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|database
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|contains
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
name|database
operator|.
name|getString
argument_list|(
name|string
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|hasStringLabelFindsString ()
specifier|public
name|void
name|hasStringLabelFindsString
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"DSP"
argument_list|,
literal|"Digital Signal Processing"
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|database
operator|.
name|hasStringLabel
argument_list|(
literal|"DSP"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|database
operator|.
name|hasStringLabel
argument_list|(
literal|"VLSI"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|KeyCollisionException
operator|.
name|class
argument_list|)
DECL|method|addSameStringLabelTwiceThrowsKeyCollisionException ()
specifier|public
name|void
name|addSameStringLabelTwiceThrowsKeyCollisionException
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"DSP"
argument_list|,
literal|"Digital Signal Processing"
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|string
operator|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"DSP"
argument_list|,
literal|"Digital Signal Processor"
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|KeyCollisionException
operator|.
name|class
argument_list|)
DECL|method|addSameStringIdTwiceThrowsKeyCollisionException ()
specifier|public
name|void
name|addSameStringIdTwiceThrowsKeyCollisionException
parameter_list|()
block|{
name|String
name|id
init|=
name|IdGenerator
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|id
argument_list|,
literal|"DSP"
argument_list|,
literal|"Digital Signal Processing"
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|string
operator|=
operator|new
name|BibtexString
argument_list|(
name|id
argument_list|,
literal|"VLSI"
argument_list|,
literal|"Very Large Scale Integration"
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|insertEntryPostsAddedEntryEvent ()
specifier|public
name|void
name|insertEntryPostsAddedEntryEvent
parameter_list|()
block|{
name|BibEntry
name|expectedEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|TestEventListener
name|tel
init|=
operator|new
name|TestEventListener
argument_list|()
decl_stmt|;
name|database
operator|.
name|registerListener
argument_list|(
name|tel
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|expectedEntry
argument_list|)
expr_stmt|;
name|BibEntry
name|actualEntry
init|=
name|tel
operator|.
name|getBibEntry
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|expectedEntry
argument_list|,
name|actualEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removeEntryPostsRemovedEntryEvent ()
specifier|public
name|void
name|removeEntryPostsRemovedEntryEvent
parameter_list|()
block|{
name|BibEntry
name|expectedEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|TestEventListener
name|tel
init|=
operator|new
name|TestEventListener
argument_list|()
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|expectedEntry
argument_list|)
expr_stmt|;
name|database
operator|.
name|registerListener
argument_list|(
name|tel
argument_list|)
expr_stmt|;
name|database
operator|.
name|removeEntry
argument_list|(
name|expectedEntry
argument_list|)
expr_stmt|;
name|BibEntry
name|actualEntry
init|=
name|tel
operator|.
name|getBibEntry
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|expectedEntry
argument_list|,
name|actualEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|changingEntryPostsChangeEntryEvent ()
specifier|public
name|void
name|changingEntryPostsChangeEntryEvent
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|TestEventListener
name|tel
init|=
operator|new
name|TestEventListener
argument_list|()
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|database
operator|.
name|registerListener
argument_list|(
name|tel
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"test"
argument_list|,
literal|"some value"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|entry
argument_list|,
name|tel
operator|.
name|getBibEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|correctKeyCountOne ()
specifier|public
name|void
name|correctKeyCountOne
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
name|setCiteKey
argument_list|(
literal|"AAA"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|correctKeyCountTwo ()
specifier|public
name|void
name|correctKeyCountTwo
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
name|setCiteKey
argument_list|(
literal|"AAA"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"AAA"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
argument_list|,
literal|2
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setCiteKeySameKeySameEntry ()
specifier|public
name|void
name|setCiteKeySameKeySameEntry
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
name|setCiteKey
argument_list|(
literal|"AAA"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|database
operator|.
name|setCiteKeyForEntry
argument_list|(
name|entry
argument_list|,
literal|"AAA"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setCiteKeyRemoveKey ()
specifier|public
name|void
name|setCiteKeyRemoveKey
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
name|setCiteKey
argument_list|(
literal|"AAA"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|database
operator|.
name|setCiteKeyForEntry
argument_list|(
name|entry
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
argument_list|,
literal|0
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
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setCiteKeyDifferentKeySameEntry ()
specifier|public
name|void
name|setCiteKeyDifferentKeySameEntry
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
name|setCiteKey
argument_list|(
literal|"AAA"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|database
operator|.
name|setCiteKeyForEntry
argument_list|(
name|entry
argument_list|,
literal|"BBB"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"BBB"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setCiteKeySameKeyDifferentEntries ()
specifier|public
name|void
name|setCiteKeySameKeyDifferentEntries
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
name|setCiteKey
argument_list|(
literal|"AAA"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"BBB"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|database
operator|.
name|setCiteKeyForEntry
argument_list|(
name|entry
argument_list|,
literal|"AAA"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|,
name|Optional
operator|.
name|of
argument_list|(
literal|"AAA"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"BBB"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|correctKeyCountAfterRemoving ()
specifier|public
name|void
name|correctKeyCountAfterRemoving
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
name|setCiteKey
argument_list|(
literal|"AAA"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"AAA"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|database
operator|.
name|removeEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|circularStringResolving ()
specifier|public
name|void
name|circularStringResolving
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"AAA"
argument_list|,
literal|"#BBB#"
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|string
operator|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"BBB"
argument_list|,
literal|"#AAA#"
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#AAA#"
argument_list|)
argument_list|,
literal|"AAA"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#BBB#"
argument_list|)
argument_list|,
literal|"BBB"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|circularStringResolvingLongerCycle ()
specifier|public
name|void
name|circularStringResolvingLongerCycle
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"AAA"
argument_list|,
literal|"#BBB#"
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|string
operator|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"BBB"
argument_list|,
literal|"#CCC#"
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|string
operator|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"CCC"
argument_list|,
literal|"#DDD#"
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|string
operator|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"DDD"
argument_list|,
literal|"#AAA#"
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#AAA#"
argument_list|)
argument_list|,
literal|"AAA"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#BBB#"
argument_list|)
argument_list|,
literal|"BBB"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#CCC#"
argument_list|)
argument_list|,
literal|"CCC"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#DDD#"
argument_list|)
argument_list|,
literal|"DDD"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|resolveForStringsMonth ()
specifier|public
name|void
name|resolveForStringsMonth
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#jan#"
argument_list|)
argument_list|,
literal|"January"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|resolveForStringsSurroundingContent ()
specifier|public
name|void
name|resolveForStringsSurroundingContent
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"AAA"
argument_list|,
literal|"aaa"
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"aa#AAA#AAA"
argument_list|)
argument_list|,
literal|"aaaaaAAA"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|resolveForStringsOddHashMarkAtTheEnd ()
specifier|public
name|void
name|resolveForStringsOddHashMarkAtTheEnd
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"AAA"
argument_list|,
literal|"aaa"
argument_list|)
decl_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"AAA#AAA#AAA#"
argument_list|)
argument_list|,
literal|"AAAaaaAAA#"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

