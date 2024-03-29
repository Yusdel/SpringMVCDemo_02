'use strict';

/* Controller */

angular.module('app').controller('GestPromoController',
	[
		'$scope',
		'$log',
		'$location',
		'$timeout',
		'PromoService',
		function ($scope, $log, $location, $timeout, PromoService) {

			$scope.dettPromo = {
				id: '',
				riga: '',
				codart: '',
				codfid: '',
				inizio: '',
				fine: '',
				oggetto: '',
				isfid: '',
				tipoPromo: {
					id: ''
				}
			};

			$scope.promo = {
				idPromo: '',
				anno: '',
				codice: '',
				descrizione: '',
				dettPromo: []
			};



			$scope.promozioni = [];

			// ******** CREATE **********
			$scope.createPromo = function (promo) {
				PromoService
					.InsPromo(promo)
					.then(
						okPromo(),
						function (errResponse) {
							//showmsg("ERRORE: Impossibile Creare la Promozione");
							console.error('Errore Creazione Promozione');
						});
			};

			// ******** DELETE **********
			$scope.deletePromo = function (idPromo) {
				PromoService
					.DelPromo(idPromo)
					.then(
						okPromo,
						function (errResponse) {
							console.error(errResponse);
						});
			};

			// ******** DELETE ROW PROMO**********
			$scope.deleteRowPromo = function (idRowPromo) {
				PromoService
					.DelDettPromo(idRowPromo)
					.then(
						okPromo(),
						function (errResponse) {
							console.error(errResponse);
						});
			};

			// ******** SELECT ALL **********
			$scope.selectAllPromo = function () {
				PromoService
					.SelAllPromo()
					.then(
						function (d) {
							$log.log('Caricamento Tutte Le Promo', d);
							$scope.promozioni = d;

						},
						function (errResponse) {

							if (errResponse.status == "404") {

								showmsg("Promo non Trovata");

							} else if (errResponse.status == "-1") {

								showmsg("Errore: Servizio non attivo o non raggiungibile!");
							}

						});
			};

			// ******** SELECT **********
			$scope.selectPromo = function (idPromo) {
				PromoService
					.SelPromo(idPromo)
					.then(
						function (d) {
							$log.log('Caricamento Promo', d);

							$scope.promozioni.length = 0;
							$scope.promozioni = d;

							//$scope.edit();
						},
						function (errResponse) {

							if (errResponse.status == "404") {

								$log.log('Promo non Trovata');
								//showmsg("Promo non Trovata");

							}
							else if (errResponse.status == "-1") {
								$log.log('Errore: Servizio non attivo o non raggiungibile!');
								//showmsg("Errore: Servizio non attivo o non raggiungibile!");
							}

						});
			};

			// ******** okInsPromo **********
			var okPromo = function () {
				setTimeout(function () {
					if ($scope.IdPromo.length > 0) {
						$scope.selectPromo($scope.IdPromo);
						resetPromo();
					}

					$scope.$apply(); //this triggers a $digest
				}, 500);
			};

			var resetPromo = function () {

				$log.log('Avvio funzione reset');

				$scope.dettPromo = {
					id: '',
					riga: '',
					codart: '',
					codfid: '',
					inizio: '',
					fine: '',
					oggetto: '',
					isfid: '',
					tipoPromo: {
						descrizione: '',
						id: ''
					}
				};

				$scope.frmPromo.$setPristine();  // reset Form

				//var txtbarcode = $window.document.getElementById('barcode');
				//txtbarcode.focus();

			}

			$scope.edit = function () {
				$scope.promo = angular.copy($scope.promozioni);
			}

			// ******** Funzione Salva **********
			$scope.salva = function () {

				$log.log('Avvio funzione salva');

				$scope.dettPromo.isfid = ($scope.dettPromo.isfid == true) ? "Si" : "No";


				$log.log('Salvataggio DettPromo', $scope.dettPromo);

				if ($scope.Tipo == 2) {
					var riga = $scope.promozioni.dettPromo.length + 1
					$scope.dettPromo.riga = riga;

					$scope.promozioni.dettPromo.push($scope.dettPromo);
				}
				else {
					$scope.dettPromo.riga = 1;
					//$scope.promozioni.dettPromo = $scope.dettPromo;
					$scope.promozioni.dettPromo.push($scope.dettPromo);

					//$scope.promozioni.depRifPromo.push("525");
				}

				$scope.createPromo($scope.promozioni);

			};

			// ******** Funzione Eliminazione Riga **********
			$scope.deleteRiga = function (Id) {
				$log.log('Avvio funzione Eliminazione Riga ' + Id);

				$scope.deleteRowPromo(Id);
			}

			//STARTUP FUNCTION Funzione di avvio
			var startAction = function () {

				$log.log('Avvio DettPromo Controller');

				$scope.titolo = "Gestione Promozione";

				var url = $location.absUrl().split('/');

				if (url[4].length > 10) {
					$scope.Tipo = 2; //Modifica

					$scope.IdPromo = url[4];
					$log.log("IdPromo: " + $scope.IdPromo);
					$scope.selectPromo($scope.IdPromo);
				}
				else {
					$scope.Tipo = 1; //Inserimento

					$scope.IdPromo = CreateGuid();
					$log.log("IdPromo: " + $scope.IdPromo);
					$scope.promozioni = $scope.promo;

					$scope.promozioni.idPromo = $scope.IdPromo;
					$scope.promozioni.anno = (new Date()).getFullYear();
				}

				$scope.tipopromo =
				{
					availableOptions: [
						{
							value: '1',
							name: 'TAGLIO PREZZO'
						},
						{
							value: '2',
							name: 'SCONTO PERCENTUALE'
						},
						{
							value: '3',
							name: 'BOLLINI'
						},
						{
							value: '4',
							name: 'NxM'
						}]
				};


			}

			function CreateGuid() {
				function _p8(s) {
					var p = (Math.random().toString(16) + "000000000").substr(2, 8);
					return s ? "-" + p.substr(0, 4) + "-" + p.substr(4, 4) : p;
				}

				return _p8() + _p8(true) + _p8(true) + _p8();
			}

			var init = function () {
				startAction();
			};

			init();

		}]);