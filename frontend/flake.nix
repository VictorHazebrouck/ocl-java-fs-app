{
  description = "Cypress support";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs =
    {
      self,
      nixpkgs,
    }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs { inherit system; };
    in
    {
      devShells.${system}.default = pkgs.mkShell rec {
        buildInputs = [
          pkgs.glib
          pkgs.nss
          pkgs.nspr
          pkgs.dbus
          pkgs.atk
          pkgs.cups
          pkgs.gtk3
          pkgs.pango
          pkgs.cairo
          pkgs.glib
          pkgs.libgbm
          pkgs.expat
          pkgs.alsa-lib

          pkgs.libx11
          pkgs.libxcb
          pkgs.libxkbcommon
          pkgs.libXcomposite
          pkgs.libXdamage
          pkgs.libXext
          pkgs.libXfixes
          pkgs.libXrandr
        ];

        LD_LIBRARY_PATH = "${pkgs.lib.makeLibraryPath buildInputs}";
      };
    };
}
