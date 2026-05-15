import { defineConfig } from "cypress";
import plugin from "./cypress/plugins";

export default defineConfig({
  videosFolder: "cypress/videos",
  screenshotsFolder: "cypress/screenshots",
  fixturesFolder: "cypress/fixtures",
  video: false,
  e2e: {
    setupNodeEvents(on, config) {
      return plugin(on, config);
    },
    baseUrl: "http://localhost:4200",
    excludeSpecPattern: [
      "src/app/components/me/**", // ignore specific file
    ],
  },
});
