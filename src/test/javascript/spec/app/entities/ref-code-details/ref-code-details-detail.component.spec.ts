import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefCodeDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/ref-code-details/ref-code-details-detail.component';
import { RefCodeDetailsService } from '../../../../../../main/webapp/app/entities/ref-code-details/ref-code-details.service';
import { RefCodeDetails } from '../../../../../../main/webapp/app/entities/ref-code-details/ref-code-details.model';

describe('Component Tests', () => {

    describe('RefCodeDetails Management Detail Component', () => {
        let comp: RefCodeDetailsDetailComponent;
        let fixture: ComponentFixture<RefCodeDetailsDetailComponent>;
        let service: RefCodeDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [RefCodeDetailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefCodeDetailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefCodeDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefCodeDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefCodeDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefCodeDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refCodeDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
