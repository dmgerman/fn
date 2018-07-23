begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|SortedSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|FileChooser
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
name|exporter
operator|.
name|Exporter
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
name|Importer
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
name|logic
operator|.
name|util
operator|.
name|FileType
import|;
end_import

begin_class
DECL|class|FileFilterConverter
specifier|public
class|class
name|FileFilterConverter
block|{
DECL|field|ANY_FILE
specifier|public
specifier|static
name|FileChooser
operator|.
name|ExtensionFilter
name|ANY_FILE
init|=
operator|new
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Any file"
argument_list|)
argument_list|,
literal|"*.*"
argument_list|)
decl_stmt|;
DECL|method|FileFilterConverter ()
specifier|private
name|FileFilterConverter
parameter_list|()
block|{     }
DECL|method|toExtensionFilter (FileType fileType)
specifier|static
name|FileChooser
operator|.
name|ExtensionFilter
name|toExtensionFilter
parameter_list|(
name|FileType
name|fileType
parameter_list|)
block|{
name|String
name|description
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
name|fileType
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|(
name|description
argument_list|,
name|fileType
operator|.
name|getExtensionsWithDot
argument_list|()
argument_list|)
return|;
block|}
DECL|method|toExtensionFilter (String description, FileType fileType)
specifier|static
name|FileChooser
operator|.
name|ExtensionFilter
name|toExtensionFilter
parameter_list|(
name|String
name|description
parameter_list|,
name|FileType
name|fileType
parameter_list|)
block|{
return|return
operator|new
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|(
name|description
argument_list|,
name|fileType
operator|.
name|getExtensionsWithDot
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getImporter (FileChooser.ExtensionFilter extensionFilter, Collection<Importer> importers)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Importer
argument_list|>
name|getImporter
parameter_list|(
name|FileChooser
operator|.
name|ExtensionFilter
name|extensionFilter
parameter_list|,
name|Collection
argument_list|<
name|Importer
argument_list|>
name|importers
parameter_list|)
block|{
return|return
name|importers
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|importer
lambda|->
name|importer
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|extensionFilter
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
block|}
DECL|method|getExporter (FileChooser.ExtensionFilter extensionFilter, Collection<Exporter> exporters)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Exporter
argument_list|>
name|getExporter
parameter_list|(
name|FileChooser
operator|.
name|ExtensionFilter
name|extensionFilter
parameter_list|,
name|Collection
argument_list|<
name|Exporter
argument_list|>
name|exporters
parameter_list|)
block|{
return|return
name|exporters
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|exporter
lambda|->
name|exporter
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|extensionFilter
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
block|}
DECL|method|forAllImporters (SortedSet<Importer> importers)
specifier|public
specifier|static
name|FileChooser
operator|.
name|ExtensionFilter
name|forAllImporters
parameter_list|(
name|SortedSet
argument_list|<
name|Importer
argument_list|>
name|importers
parameter_list|)
block|{
name|List
argument_list|<
name|FileType
argument_list|>
name|fileTypes
init|=
name|importers
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Importer
operator|::
name|getFileType
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|flatExtensions
init|=
name|fileTypes
operator|.
name|stream
argument_list|()
operator|.
name|flatMap
argument_list|(
name|type
lambda|->
name|type
operator|.
name|getExtensionsWithDot
argument_list|()
operator|.
name|stream
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Available import formats"
argument_list|)
argument_list|,
name|flatExtensions
argument_list|)
return|;
block|}
DECL|method|importerToExtensionFilter (Collection<Importer> importers)
specifier|public
specifier|static
name|List
argument_list|<
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|>
name|importerToExtensionFilter
parameter_list|(
name|Collection
argument_list|<
name|Importer
argument_list|>
name|importers
parameter_list|)
block|{
return|return
name|importers
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|importer
lambda|->
name|toExtensionFilter
argument_list|(
name|importer
operator|.
name|getName
argument_list|()
argument_list|,
name|importer
operator|.
name|getFileType
argument_list|()
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|exporterToExtensionFilter (Collection<Exporter> exporters)
specifier|public
specifier|static
name|List
argument_list|<
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|>
name|exporterToExtensionFilter
parameter_list|(
name|Collection
argument_list|<
name|Exporter
argument_list|>
name|exporters
parameter_list|)
block|{
return|return
name|exporters
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|exporter
lambda|->
name|toExtensionFilter
argument_list|(
name|exporter
operator|.
name|getName
argument_list|()
argument_list|,
name|exporter
operator|.
name|getFileType
argument_list|()
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

