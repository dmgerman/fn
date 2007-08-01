begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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
name|ByteArrayInputStream
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

begin_class
DECL|class|RtfSelection
specifier|public
class|class
name|RtfSelection
implements|implements
name|Transferable
block|{
DECL|field|rtfFlavor
name|DataFlavor
name|rtfFlavor
decl_stmt|;
DECL|field|supportedFlavors
name|DataFlavor
index|[]
name|supportedFlavors
decl_stmt|;
DECL|field|content
specifier|private
name|String
name|content
decl_stmt|;
DECL|method|RtfSelection (String s)
specifier|public
name|RtfSelection
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|content
operator|=
name|s
expr_stmt|;
try|try
block|{
name|rtfFlavor
operator|=
operator|new
name|DataFlavor
argument_list|(
literal|"text/rtf; class=java.io.InputStream"
argument_list|)
expr_stmt|;
name|supportedFlavors
operator|=
operator|new
name|DataFlavor
index|[]
block|{
name|rtfFlavor
block|,
name|DataFlavor
operator|.
name|stringFlavor
block|}
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
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
name|rtfFlavor
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
DECL|method|getTransferDataFlavors ()
specifier|public
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|DataFlavor
index|[]
name|getTransferDataFlavors
parameter_list|()
block|{
comment|//System.out.println("..");
return|return
name|supportedFlavors
return|;
block|}
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
throws|,
name|IOException
block|{
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
comment|//System.out.println("Delivering string data.");
return|return
name|content
return|;
block|}
elseif|else
if|if
condition|(
name|flavor
operator|.
name|equals
argument_list|(
name|rtfFlavor
argument_list|)
condition|)
block|{
comment|//System.out.println("Delivering rtf data.");
name|byte
index|[]
name|byteArray
init|=
name|content
operator|.
name|getBytes
argument_list|()
decl_stmt|;
return|return
operator|new
name|ByteArrayInputStream
argument_list|(
name|byteArray
argument_list|)
return|;
block|}
throw|throw
operator|new
name|UnsupportedFlavorException
argument_list|(
name|flavor
argument_list|)
throw|;
block|}
block|}
end_class

end_unit

