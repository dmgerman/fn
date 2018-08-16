begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|DialogService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|WordSuggestionProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|CurrentThreadTaskExecutor
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
name|integrity
operator|.
name|FieldCheckers
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
name|assertTrue
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

begin_class
DECL|class|IdentifierEditorViewModelTest
specifier|public
class|class
name|IdentifierEditorViewModelTest
block|{
DECL|field|viewModel
specifier|private
name|IdentifierEditorViewModel
name|viewModel
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
name|viewModel
operator|=
operator|new
name|IdentifierEditorViewModel
argument_list|(
literal|"DOI"
argument_list|,
operator|new
name|WordSuggestionProvider
argument_list|(
literal|"DOI"
argument_list|)
argument_list|,
operator|new
name|CurrentThreadTaskExecutor
argument_list|()
argument_list|,
name|mock
argument_list|(
name|DialogService
operator|.
name|class
argument_list|)
argument_list|,
name|mock
argument_list|(
name|FieldCheckers
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|validIdentifierIsNotPresentIsTrueForEmptyText ()
specifier|public
name|void
name|validIdentifierIsNotPresentIsTrueForEmptyText
parameter_list|()
throws|throws
name|Exception
block|{
name|assertTrue
argument_list|(
name|viewModel
operator|.
name|validIdentifierIsNotPresentProperty
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

