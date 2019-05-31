begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|texparser
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
name|util
operator|.
name|List
import|;
end_import

begin_interface
DECL|interface|TexParser
specifier|public
interface|interface
name|TexParser
block|{
comment|/**      * @param texFile Path to a TEX file      * @return a TexParserResult, which contains the generated BibDatabase and all data related to the bibliographic      * entries      */
DECL|method|parse (Path texFile)
name|TexParserResult
name|parse
parameter_list|(
name|Path
name|texFile
parameter_list|)
function_decl|;
comment|/**      * @param texFiles List of Path objects linked to a TEX file      * @return a list of TexParserResult objects, which contains the generated BibDatabase and all data related to the      * bibliographic entries      */
DECL|method|parse (List<Path> texFiles)
name|TexParserResult
name|parse
parameter_list|(
name|List
argument_list|<
name|Path
argument_list|>
name|texFiles
parameter_list|)
function_decl|;
block|}
end_interface

end_unit
