begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.util
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
package|;
end_package

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
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_comment
comment|/**  * This {@link FileUpdateMonitor} does nothing.  * Normally, you want to use {@link org.jabref.gui.util.DefaultFileUpdateMonitor} except if you don't care about updates.  */
end_comment

begin_class
DECL|class|DummyFileUpdateMonitor
specifier|public
class|class
name|DummyFileUpdateMonitor
implements|implements
name|FileUpdateMonitor
block|{
annotation|@
name|Override
DECL|method|addListenerForFile (Path file, FileUpdateListener listener)
specifier|public
name|void
name|addListenerForFile
parameter_list|(
name|Path
name|file
parameter_list|,
name|FileUpdateListener
name|listener
parameter_list|)
throws|throws
name|IOException
block|{      }
annotation|@
name|Override
DECL|method|removeListener (Path path, FileUpdateListener listener)
specifier|public
name|void
name|removeListener
parameter_list|(
name|Path
name|path
parameter_list|,
name|FileUpdateListener
name|listener
parameter_list|)
block|{      }
block|}
end_class

end_unit

