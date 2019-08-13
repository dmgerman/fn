begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.pdf
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|pdf
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|Map
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
name|database
operator|.
name|BibDatabaseContext
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
name|metadata
operator|.
name|FilePreferences
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
name|pdf
operator|.
name|FileAnnotation
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
name|mockito
operator|.
name|ArgumentMatchers
operator|.
name|any
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|when
import|;
end_import

begin_class
DECL|class|EntryAnnotationImporterTest
specifier|public
class|class
name|EntryAnnotationImporterTest
block|{
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
init|=
name|mock
argument_list|(
name|BibDatabaseContext
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|BeforeEach
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
name|when
argument_list|(
name|databaseContext
operator|.
name|getFileDirectoriesAsPaths
argument_list|(
name|any
argument_list|()
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"src/test/resources/pdfs/"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|readEntryExampleThesis ()
specifier|public
name|void
name|readEntryExampleThesis
parameter_list|()
block|{
comment|//given
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|FILE
argument_list|,
literal|":thesis-example.pdf:PDF"
argument_list|)
expr_stmt|;
name|EntryAnnotationImporter
name|entryAnnotationImporter
init|=
operator|new
name|EntryAnnotationImporter
argument_list|(
name|entry
argument_list|)
decl_stmt|;
comment|//when
name|Map
argument_list|<
name|Path
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
name|annotations
init|=
name|entryAnnotationImporter
operator|.
name|importAnnotationsFromFiles
argument_list|(
name|databaseContext
argument_list|,
name|mock
argument_list|(
name|FilePreferences
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
comment|//then
name|int
name|fileCounter
init|=
literal|0
decl_stmt|;
name|int
name|annotationCounter
init|=
literal|0
decl_stmt|;
for|for
control|(
name|List
argument_list|<
name|FileAnnotation
argument_list|>
name|annotationsOfFile
range|:
name|annotations
operator|.
name|values
argument_list|()
control|)
block|{
name|fileCounter
operator|++
expr_stmt|;
name|annotationCounter
operator|+=
name|annotationsOfFile
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|fileCounter
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|annotationCounter
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

