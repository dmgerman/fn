begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: 12.mar.2005  * Time: 14:58:21  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|DisplayPanel
specifier|public
class|class
name|DisplayPanel
block|{
DECL|field|panel
specifier|private
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|activeEntry
specifier|private
name|BibtexEntry
name|activeEntry
init|=
literal|null
decl_stmt|;
DECL|method|DisplayPanel ()
specifier|public
name|DisplayPanel
parameter_list|()
block|{
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|setEntry (BibtexEntry entry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|activeEntry
operator|=
name|entry
expr_stmt|;
block|}
DECL|method|getPanel ()
specifier|public
name|JPanel
name|getPanel
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
block|}
end_class

end_unit

