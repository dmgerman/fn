begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Clipboard
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|DataFlavor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Transferable
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ImportException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ImportFormatReader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ImportFormatReader
operator|.
name|UnknownFormatImport
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ParserResult
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
import|import
name|org
operator|.
name|mockito
operator|.
name|ArgumentMatchers
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
name|*
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
DECL|class|ClipBoardManagerTest
specifier|public
class|class
name|ClipBoardManagerTest
block|{
DECL|field|clipBoardManager
specifier|private
name|ClipBoardManager
name|clipBoardManager
decl_stmt|;
DECL|field|content
specifier|private
name|Transferable
name|content
decl_stmt|;
DECL|field|importFormatReader
specifier|private
name|ImportFormatReader
name|importFormatReader
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|importFormatReader
operator|=
name|mock
argument_list|(
name|ImportFormatReader
operator|.
name|class
argument_list|)
expr_stmt|;
name|Clipboard
name|clipboard
init|=
name|mock
argument_list|(
name|Clipboard
operator|.
name|class
argument_list|)
decl_stmt|;
name|clipBoardManager
operator|=
operator|new
name|ClipBoardManager
argument_list|(
name|clipboard
argument_list|,
name|importFormatReader
argument_list|)
expr_stmt|;
name|content
operator|=
name|mock
argument_list|(
name|Transferable
operator|.
name|class
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|clipboard
operator|.
name|getContents
argument_list|(
name|ArgumentMatchers
operator|.
name|any
argument_list|()
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|extractBibEntriesFromClipboardParsesStringFlavor ()
specifier|public
name|void
name|extractBibEntriesFromClipboardParsesStringFlavor
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|expected
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|expected
operator|.
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setCiteKey
argument_list|(
literal|"canh05"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Crowston, K. and Annabi, H."
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Title A"
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|content
operator|.
name|isDataFlavorSupported
argument_list|(
name|TransferableBibtexEntry
operator|.
name|ENTRY_FLAVOR
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|content
operator|.
name|isDataFlavorSupported
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|String
name|data
init|=
literal|"@article{canh05,  author = {Crowston, K. and Annabi, H.},\n"
operator|+
literal|"  title = {Title A}}\n"
decl_stmt|;
name|when
argument_list|(
name|content
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|data
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|importFormatReader
operator|.
name|importUnknownFormat
argument_list|(
name|data
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
operator|new
name|UnknownFormatImport
argument_list|(
literal|"abc"
argument_list|,
operator|new
name|ParserResult
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|expected
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actual
init|=
name|clipBoardManager
operator|.
name|extractBibEntriesFromClipboard
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|expected
argument_list|)
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|extractBibEntriesFromClipboardReturnsEmptyIfStringparsingFailed ()
specifier|public
name|void
name|extractBibEntriesFromClipboardReturnsEmptyIfStringparsingFailed
parameter_list|()
throws|throws
name|Exception
block|{
name|when
argument_list|(
name|content
operator|.
name|isDataFlavorSupported
argument_list|(
name|TransferableBibtexEntry
operator|.
name|ENTRY_FLAVOR
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|content
operator|.
name|isDataFlavorSupported
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|content
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|"testData"
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|importFormatReader
operator|.
name|importUnknownFormat
argument_list|(
literal|"testData"
argument_list|)
argument_list|)
operator|.
name|thenThrow
argument_list|(
operator|new
name|ImportException
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actual
init|=
name|clipBoardManager
operator|.
name|extractBibEntriesFromClipboard
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|()
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

