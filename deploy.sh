#!/bin/bash

# echo "🛠️ Build en cours..."
# docker build -t mon-app .

# echo "🚀 Déploiement en cours vers CapRover..."
# caprover deploy --default

#!/bin/bash

echo "🔧 Build de l'application..."
./mvnw -Dmaven.test.skip=true clean package

echo "📦 Déploiement via CapRover CLI..."
caprover deploy


echo "✅ Déploiement terminé !"
echo "🔗 Lien de l'application : https://app.lesmysteresdelegypteantique.fr"