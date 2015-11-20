begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
package|;
end_package

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
name|Ignore
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

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Nov 9, 2007  * Time: 7:04:25 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|DuplicateCheckTest
specifier|public
class|class
name|DuplicateCheckTest
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
annotation|@
name|Ignore
DECL|method|testDuplicateDetection ()
specifier|public
name|void
name|testDuplicateDetection
parameter_list|()
block|{
name|BibtexEntry
name|one
init|=
operator|new
name|BibtexEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|BibtexEntry
name|two
init|=
operator|new
name|BibtexEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|one
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Billy Bob"
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Billy Bob"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|one
argument_list|,
name|two
argument_list|)
argument_list|)
expr_stmt|;
comment|//TODO algorithm thinks bob and joyce is the same with high accuracy
name|two
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"James Joyce"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|one
argument_list|,
name|two
argument_list|)
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Billy Bob"
argument_list|)
expr_stmt|;
name|two
operator|.
name|setType
argument_list|(
name|BibtexEntryTypes
operator|.
name|BOOK
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|one
argument_list|,
name|two
argument_list|)
argument_list|)
expr_stmt|;
name|two
operator|.
name|setType
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|one
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2005"
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2005"
argument_list|)
expr_stmt|;
name|one
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"A title"
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"A title"
argument_list|)
expr_stmt|;
name|one
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"A"
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"A"
argument_list|)
expr_stmt|;
name|one
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|one
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"21"
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"21"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|one
argument_list|,
name|two
argument_list|)
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"22"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|one
argument_list|,
name|two
argument_list|)
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Another title"
argument_list|)
expr_stmt|;
name|two
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"B"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|one
argument_list|,
name|two
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testWordCorrelation ()
specifier|public
name|void
name|testWordCorrelation
parameter_list|()
block|{
name|String
name|d1
init|=
literal|"Characterization of Calanus finmarchicus habitat in the North Sea"
decl_stmt|;
name|String
name|d2
init|=
literal|"Characterization of Calunus finmarchicus habitat in the North Sea"
decl_stmt|;
name|String
name|d3
init|=
literal|"Characterization of Calanus glacialissss habitat in the South Sea"
decl_stmt|;
name|assertEquals
argument_list|(
literal|1.0
argument_list|,
operator|(
name|DuplicateCheck
operator|.
name|correlateByWords
argument_list|(
name|d1
argument_list|,
name|d2
argument_list|,
literal|false
argument_list|)
operator|)
argument_list|,
literal|0.01
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0.88
argument_list|,
operator|(
name|DuplicateCheck
operator|.
name|correlateByWords
argument_list|(
name|d1
argument_list|,
name|d3
argument_list|,
literal|false
argument_list|)
operator|)
argument_list|,
literal|0.01
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0.88
argument_list|,
operator|(
name|DuplicateCheck
operator|.
name|correlateByWords
argument_list|(
name|d2
argument_list|,
name|d3
argument_list|,
literal|false
argument_list|)
operator|)
argument_list|,
literal|0.01
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

