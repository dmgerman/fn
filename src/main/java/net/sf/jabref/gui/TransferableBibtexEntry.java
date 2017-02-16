begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
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
name|awt
operator|.
name|datatransfer
operator|.
name|UnsupportedFlavorException
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
name|StringWriter
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
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|org
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
name|logic
operator|.
name|bibtex
operator|.
name|BibEntryWriter
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
name|logic
operator|.
name|bibtex
operator|.
name|LatexFieldFormatter
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|BibDatabaseMode
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

begin_comment
comment|/*  * A transferable object containing an array of BibEntry objects. Used  * for copy-paste operations.  */
end_comment

begin_class
DECL|class|TransferableBibtexEntry
specifier|public
class|class
name|TransferableBibtexEntry
implements|implements
name|Transferable
block|{
DECL|field|data
specifier|private
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|data
decl_stmt|;
DECL|field|entryFlavor
specifier|public
specifier|static
specifier|final
name|DataFlavor
name|entryFlavor
init|=
operator|new
name|DataFlavor
argument_list|(
name|BibEntry
operator|.
name|class
argument_list|,
literal|"JabRef entry"
argument_list|)
decl_stmt|;
DECL|method|TransferableBibtexEntry (List<BibEntry> bes)
specifier|public
name|TransferableBibtexEntry
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bes
parameter_list|)
block|{
name|this
operator|.
name|data
operator|=
name|bes
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getTransferDataFlavors ()
specifier|public
name|DataFlavor
index|[]
name|getTransferDataFlavors
parameter_list|()
block|{
return|return
operator|new
name|DataFlavor
index|[]
block|{
name|TransferableBibtexEntry
operator|.
name|entryFlavor
block|,
name|DataFlavor
operator|.
name|stringFlavor
block|}
return|;
block|}
annotation|@
name|Override
DECL|method|isDataFlavorSupported (DataFlavor flavor)
specifier|public
name|boolean
name|isDataFlavorSupported
parameter_list|(
name|DataFlavor
name|flavor
parameter_list|)
block|{
return|return
name|flavor
operator|.
name|equals
argument_list|(
name|TransferableBibtexEntry
operator|.
name|entryFlavor
argument_list|)
operator|||
name|flavor
operator|.
name|equals
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getTransferData (DataFlavor flavor)
specifier|public
name|Object
name|getTransferData
parameter_list|(
name|DataFlavor
name|flavor
parameter_list|)
throws|throws
name|UnsupportedFlavorException
block|{
if|if
condition|(
name|flavor
operator|.
name|equals
argument_list|(
name|TransferableBibtexEntry
operator|.
name|entryFlavor
argument_list|)
condition|)
block|{
return|return
name|data
return|;
block|}
elseif|else
if|if
condition|(
name|flavor
operator|.
name|equals
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
condition|)
block|{
try|try
block|{
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|BibEntryWriter
name|bibtexEntryWriter
init|=
operator|new
name|BibEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|data
control|)
block|{
name|bibtexEntryWriter
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|sw
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
block|}
return|return
name|sw
operator|.
name|toString
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not paste entry as text:"
argument_list|)
operator|+
literal|"\n"
operator|+
name|ex
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clipboard"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return
literal|""
return|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|UnsupportedFlavorException
argument_list|(
name|flavor
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

