begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database
package|package
name|org
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
name|Collection
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
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|StandardField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|UnknownField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|types
operator|.
name|StandardEntryType
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|BeforeEach
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertThrows
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertTrue
import|;
end_import

begin_class
DECL|class|BibDatabaseTest
specifier|public
class|class
name|BibDatabaseTest
block|{
DECL|field|database
specifier|private
name|BibDatabase
name|database
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|database
operator|=
operator|new
name|BibDatabase
argument_list|()
expr_stmt|;
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
literal|1
argument_list|,
name|database
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|database
operator|.
name|getEntryCount
argument_list|()
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
argument_list|()
decl_stmt|;
name|entry1
operator|.
name|setId
argument_list|(
name|entry0
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|assertThrows
argument_list|(
name|KeyCollisionException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|database
operator|.
name|insertEntry
argument_list|(
name|entry1
argument_list|)
argument_list|)
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
DECL|method|insertNullEntryThrowsException ()
specifier|public
name|void
name|insertNullEntryThrowsException
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|NullPointerException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|database
operator|.
name|insertEntry
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removeNullEntryThrowsException ()
specifier|public
name|void
name|removeNullEntryThrowsException
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|NullPointerException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|database
operator|.
name|removeEntry
argument_list|(
literal|null
argument_list|)
argument_list|)
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
literal|1
argument_list|,
name|database
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|database
operator|.
name|getStringCount
argument_list|()
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
literal|0
argument_list|,
name|database
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|database
operator|.
name|getStringCount
argument_list|()
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
DECL|method|setSingleStringAsCollection ()
specifier|public
name|void
name|setSingleStringAsCollection
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
literal|"DSP"
argument_list|,
literal|"Digital Signal Processing"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibtexString
argument_list|>
name|strings
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|string
argument_list|)
decl_stmt|;
name|database
operator|.
name|setStrings
argument_list|(
name|strings
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|string
argument_list|)
argument_list|,
name|database
operator|.
name|getStringByName
argument_list|(
literal|"DSP"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setStringAsCollectionWithUpdatedContentOverridesString ()
specifier|public
name|void
name|setStringAsCollectionWithUpdatedContentOverridesString
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
literal|"DSP"
argument_list|,
literal|"Digital Signal Processing"
argument_list|)
decl_stmt|;
name|BibtexString
name|newContent
init|=
operator|new
name|BibtexString
argument_list|(
literal|"DSP"
argument_list|,
literal|"ABCD"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibtexString
argument_list|>
name|strings
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|string
argument_list|,
name|newContent
argument_list|)
decl_stmt|;
name|database
operator|.
name|setStrings
argument_list|(
name|strings
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|newContent
argument_list|)
argument_list|,
name|database
operator|.
name|getStringByName
argument_list|(
literal|"DSP"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setStringAsCollectionWithNewContent ()
specifier|public
name|void
name|setStringAsCollectionWithNewContent
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
literal|"DSP"
argument_list|,
literal|"Digital Signal Processing"
argument_list|)
decl_stmt|;
name|BibtexString
name|vlsi
init|=
operator|new
name|BibtexString
argument_list|(
literal|"VLSI"
argument_list|,
literal|"Very Large Scale Integration"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibtexString
argument_list|>
name|strings
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|string
argument_list|,
name|vlsi
argument_list|)
decl_stmt|;
name|database
operator|.
name|setStrings
argument_list|(
name|strings
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|string
argument_list|)
argument_list|,
name|database
operator|.
name|getStringByName
argument_list|(
literal|"DSP"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|vlsi
argument_list|)
argument_list|,
name|database
operator|.
name|getStringByName
argument_list|(
literal|"VLSI"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
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
specifier|final
name|BibtexString
name|finalString
init|=
operator|new
name|BibtexString
argument_list|(
literal|"DSP"
argument_list|,
literal|"Digital Signal Processor"
argument_list|)
decl_stmt|;
name|assertThrows
argument_list|(
name|KeyCollisionException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|database
operator|.
name|addString
argument_list|(
name|finalString
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addSameStringIdTwiceThrowsKeyCollisionException ()
specifier|public
name|void
name|addSameStringIdTwiceThrowsKeyCollisionException
parameter_list|()
block|{
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
literal|"DSP"
argument_list|,
literal|"Digital Signal Processing"
argument_list|)
decl_stmt|;
name|string
operator|.
name|setId
argument_list|(
literal|"duplicateid"
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|string
argument_list|)
expr_stmt|;
specifier|final
name|BibtexString
name|finalString
init|=
operator|new
name|BibtexString
argument_list|(
literal|"VLSI"
argument_list|,
literal|"Very Large Scale Integration"
argument_list|)
decl_stmt|;
name|finalString
operator|.
name|setId
argument_list|(
literal|"duplicateid"
argument_list|)
expr_stmt|;
name|assertThrows
argument_list|(
name|KeyCollisionException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|database
operator|.
name|addString
argument_list|(
name|finalString
argument_list|)
argument_list|)
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
operator|new
name|UnknownField
argument_list|(
literal|"test"
argument_list|)
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
literal|1
argument_list|,
name|database
operator|.
name|getDuplicationChecker
argument_list|()
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
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
literal|2
argument_list|,
name|database
operator|.
name|getDuplicationChecker
argument_list|()
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
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
literal|1
argument_list|,
name|database
operator|.
name|getDuplicationChecker
argument_list|()
operator|.
name|getNumberOfKeyOccurrences
argument_list|(
literal|"AAA"
argument_list|)
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
literal|"AAA"
argument_list|,
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#AAA#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"BBB"
argument_list|,
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#BBB#"
argument_list|)
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
literal|"AAA"
argument_list|,
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#AAA#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"BBB"
argument_list|,
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#BBB#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"CCC"
argument_list|,
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#CCC#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"DDD"
argument_list|,
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#DDD#"
argument_list|)
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
literal|"January"
argument_list|,
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"#jan#"
argument_list|)
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
literal|"aaaaaAAA"
argument_list|,
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"aa#AAA#AAA"
argument_list|)
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
literal|"AAAaaaAAA#"
argument_list|,
name|database
operator|.
name|resolveForStrings
argument_list|(
literal|"AAA#AAA#AAA#"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getUsedStrings ()
specifier|public
name|void
name|getUsedStrings
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
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"#AAA#"
argument_list|)
expr_stmt|;
name|BibtexString
name|tripleA
init|=
operator|new
name|BibtexString
argument_list|(
literal|"AAA"
argument_list|,
literal|"Some other #BBB#"
argument_list|)
decl_stmt|;
name|BibtexString
name|tripleB
init|=
operator|new
name|BibtexString
argument_list|(
literal|"BBB"
argument_list|,
literal|"Some more text"
argument_list|)
decl_stmt|;
name|BibtexString
name|tripleC
init|=
operator|new
name|BibtexString
argument_list|(
literal|"CCC"
argument_list|,
literal|"Even more text"
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|BibtexString
argument_list|>
name|stringSet
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
name|stringSet
operator|.
name|add
argument_list|(
name|tripleA
argument_list|)
expr_stmt|;
name|stringSet
operator|.
name|add
argument_list|(
name|tripleB
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|tripleA
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|tripleB
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|tripleC
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|BibtexString
argument_list|>
name|usedStrings
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|database
operator|.
name|getUsedStrings
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|stringSet
argument_list|,
name|usedStrings
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getUsedStringsSingleString ()
specifier|public
name|void
name|getUsedStringsSingleString
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
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"#AAA#"
argument_list|)
expr_stmt|;
name|BibtexString
name|tripleA
init|=
operator|new
name|BibtexString
argument_list|(
literal|"AAA"
argument_list|,
literal|"Some other text"
argument_list|)
decl_stmt|;
name|BibtexString
name|tripleB
init|=
operator|new
name|BibtexString
argument_list|(
literal|"BBB"
argument_list|,
literal|"Some more text"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibtexString
argument_list|>
name|strings
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|strings
operator|.
name|add
argument_list|(
name|tripleA
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|tripleA
argument_list|)
expr_stmt|;
name|database
operator|.
name|addString
argument_list|(
name|tripleB
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibtexString
argument_list|>
name|usedStrings
init|=
operator|(
name|List
argument_list|<
name|BibtexString
argument_list|>
operator|)
name|database
operator|.
name|getUsedStrings
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|strings
argument_list|,
name|usedStrings
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getUsedStringsNoString ()
specifier|public
name|void
name|getUsedStringsNoString
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
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Oscar Gustafsson"
argument_list|)
expr_stmt|;
name|BibtexString
name|string
init|=
operator|new
name|BibtexString
argument_list|(
literal|"AAA"
argument_list|,
literal|"Some other text"
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
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|BibtexString
argument_list|>
name|usedStrings
init|=
name|database
operator|.
name|getUsedStrings
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|usedStrings
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getEntriesSortedWithTwoEntries ()
specifier|public
name|void
name|getEntriesSortedWithTwoEntries
parameter_list|()
block|{
name|BibEntry
name|entryB
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|entryB
operator|.
name|setId
argument_list|(
literal|"2"
argument_list|)
expr_stmt|;
name|BibEntry
name|entryA
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|entryB
operator|.
name|setId
argument_list|(
literal|"1"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntries
argument_list|(
name|entryB
argument_list|,
name|entryA
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|entryA
argument_list|,
name|entryB
argument_list|)
argument_list|,
name|database
operator|.
name|getEntriesSorted
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|BibEntry
operator|::
name|getId
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|preambleIsEmptyIfNotSet ()
specifier|public
name|void
name|preambleIsEmptyIfNotSet
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|database
operator|.
name|getPreamble
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setPreambleWorks ()
specifier|public
name|void
name|setPreambleWorks
parameter_list|()
block|{
name|database
operator|.
name|setPreamble
argument_list|(
literal|"Oh yeah!"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Oh yeah!"
argument_list|)
argument_list|,
name|database
operator|.
name|getPreamble
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

