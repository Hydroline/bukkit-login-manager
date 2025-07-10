Write-Host "Compiling LoginManager plugin..."

# Create output directories
New-Item -ItemType Directory -Force -Path "build\classes\java\main"
New-Item -ItemType Directory -Force -Path "build\libs"
New-Item -ItemType Directory -Force -Path "lib"

# Download Spigot API if not exists
$spigotJar = "lib\spigot-api-1.16.5-R0.1-SNAPSHOT.jar"
if (-not (Test-Path $spigotJar)) {
    Write-Host "Downloading Spigot API..."
    try {
        Invoke-WebRequest -Uri "https://hub.spigotmc.org/nexus/content/repositories/snapshots/org/spigotmc/spigot-api/1.16.5-R0.1-SNAPSHOT/spigot-api-1.16.5-R0.1-SNAPSHOT.jar" -OutFile $spigotJar
    } catch {
        Write-Host "Failed to download Spigot API. Continuing without it..."
    }
}

# Compile Java source code
Write-Host "Compiling Java source code..."
$classpath = ""
if (Test-Path $spigotJar) {
    $classpath = "-cp `"$spigotJar`""
}

$compileCmd = "javac $classpath -d `"build\classes\java\main`" -encoding UTF-8 src\main\java\org\nanfans\*.java"
Write-Host "Running: $compileCmd"
Invoke-Expression $compileCmd

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed!"
    exit 1
}

# Copy resource files
Write-Host "Copying resource files..."
if (Test-Path "src\main\resources") {
    Copy-Item -Path "src\main\resources\*" -Destination "build\classes\java\main\" -Recurse -Force
}

# Create JAR file
Write-Host "Creating JAR file..."
Set-Location "build\classes\java\main"
jar cf "..\..\..\..\build\libs\LoginManager-1.34.jar" .
Set-Location "..\..\..\.."

Write-Host "Compilation complete! JAR file is at: build\libs\LoginManager-1.34.jar"
