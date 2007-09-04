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
name|net
operator|.
name|sf
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
name|BibtexDatabase
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
name|BibtexEntry
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
name|MetaData
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_comment
comment|/**  * Skeleton implementation of MySqlExporter.  */
end_comment

begin_class
DECL|class|MySqlExport
specifier|public
class|class
name|MySqlExport
extends|extends
name|ExportFormat
block|{
DECL|method|MySqlExport ()
specifier|public
name|MySqlExport
parameter_list|()
block|{
comment|// You need to fill in the correct extension, and edit the name if necessary.
comment|// The second argument is the command-line name of the export format:
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"MySQL database"
argument_list|)
argument_list|,
literal|"mysql"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|".extension"
argument_list|)
expr_stmt|;
block|}
DECL|method|performExport (final BibtexDatabase database, final MetaData metaData, final String file, final String encoding, Set keySet)
specifier|public
name|void
name|performExport
parameter_list|(
specifier|final
name|BibtexDatabase
name|database
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
specifier|final
name|String
name|file
parameter_list|,
specifier|final
name|String
name|encoding
parameter_list|,
name|Set
name|keySet
parameter_list|)
throws|throws
name|Exception
block|{
comment|// This method gets called when the user starts the export.
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Hello world from MySqlExport"
argument_list|)
expr_stmt|;
name|File
name|outFile
init|=
operator|new
name|File
argument_list|(
name|file
argument_list|)
decl_stmt|;
comment|// One of the things you may want is to get a sorted list of which
comment|// bibtex entries need to be exported. This is done as follows:
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|sorted
init|=
name|FileActions
operator|.
name|getSortedEntries
argument_list|(
name|database
argument_list|,
name|keySet
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|// The method called above takes care of selecting all entries, or only those
comment|// that you should include in the export. So, once you get around to writing
comment|// entries, you can simply iterate over the list:
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|sorted
control|)
block|{
comment|// Do something with the entry:
name|String
name|title
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
decl_stmt|;
name|String
name|bibtexKey
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
block|}
block|}
block|}
end_class

end_unit

