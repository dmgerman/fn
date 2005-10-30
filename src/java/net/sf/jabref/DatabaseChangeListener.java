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

begin_interface
DECL|interface|DatabaseChangeListener
specifier|public
interface|interface
name|DatabaseChangeListener
block|{
DECL|method|databaseChanged (DatabaseChangeEvent e)
specifier|public
name|void
name|databaseChanged
parameter_list|(
name|DatabaseChangeEvent
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

