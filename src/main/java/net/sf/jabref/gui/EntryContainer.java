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
comment|/**  * Entry containers work on a single entry, which can be asked for  */
end_comment

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|EntryContainer
specifier|public
interface|interface
name|EntryContainer
block|{
DECL|method|getEntry ()
name|BibEntry
name|getEntry
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

