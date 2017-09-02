begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database.event
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|event
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|EntrySorter
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
name|junit
operator|.
name|Test
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
name|Comparator
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
name|assertNotNull
import|;
end_import

begin_class
DECL|class|AutosaveEventTest
specifier|public
class|class
name|AutosaveEventTest
block|{
annotation|@
name|Test
DECL|method|givenNothingWhenCreatingThenNotNull ()
specifier|public
name|void
name|givenNothingWhenCreatingThenNotNull
parameter_list|()
block|{
name|AutosaveEvent
name|e
init|=
operator|new
name|AutosaveEvent
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

