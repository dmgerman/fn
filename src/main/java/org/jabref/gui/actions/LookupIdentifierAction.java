begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
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
name|BasePanel
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
name|JabRefFrame
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
name|worker
operator|.
name|LookupIdentifiersWorker
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
name|IdFetcher
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
name|identifier
operator|.
name|Identifier
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|LookupIdentifierAction
specifier|public
class|class
name|LookupIdentifierAction
parameter_list|<
name|T
extends|extends
name|Identifier
parameter_list|>
extends|extends
name|SimpleCommand
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|LookupIdentifierAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|fetcher
specifier|private
specifier|final
name|IdFetcher
argument_list|<
name|T
argument_list|>
name|fetcher
decl_stmt|;
DECL|method|LookupIdentifierAction (JabRefFrame frame, IdFetcher<T> fetcher)
specifier|public
name|LookupIdentifierAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|IdFetcher
argument_list|<
name|T
argument_list|>
name|fetcher
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|fetcher
operator|=
name|fetcher
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
try|try
block|{
name|BasePanel
operator|.
name|runWorker
argument_list|(
operator|new
name|LookupIdentifiersWorker
argument_list|(
name|frame
argument_list|,
name|fetcher
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem running ID Worker"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

