begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

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
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|Arrays
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
name|Map
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
name|BibtexEntry
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
name|gui
operator|.
name|BibtexFields
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
name|org
operator|.
name|junit
operator|.
name|After
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
DECL|class|RegExpFileSearchTests
specifier|public
class|class
name|RegExpFileSearchTests
block|{
DECL|field|filesDirectory
specifier|private
name|String
name|filesDirectory
init|=
literal|"/src/test/resources/net/sf/jabref/imports/unlinkedFilesTestFolder"
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
block|}
annotation|@
name|Test
DECL|method|testFindFiles ()
specifier|public
name|void
name|testFindFiles
parameter_list|()
block|{
comment|//given
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
name|BibtexEntry
name|entry
init|=
operator|new
name|BibtexEntry
argument_list|(
literal|"123"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|,
literal|"pdfInDatabase"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2001"
argument_list|)
expr_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|extensions
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"pdf"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|dirs
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|File
argument_list|(
name|filesDirectory
argument_list|)
argument_list|)
decl_stmt|;
comment|//when
name|Map
argument_list|<
name|BibtexEntry
argument_list|,
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|File
argument_list|>
argument_list|>
name|result
init|=
name|RegExpFileSearch
operator|.
name|findFilesForSet
argument_list|(
name|entries
argument_list|,
name|extensions
argument_list|,
name|dirs
argument_list|,
literal|"**/[bibtexkey].*\\\\.[extension]"
argument_list|)
decl_stmt|;
comment|//then
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|result
operator|.
name|keySet
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

